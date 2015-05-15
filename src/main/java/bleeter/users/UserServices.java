package bleeter.users;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServices implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	public static final int PAGE_SIZE = 10;
	public static final int DEFAULT_PAGE = 0;
	
	public BleetUser findById(String uid) {
		return userRepository.findOne(uid);
	}

	public BleetUser findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public BleetUser createUser(BleetUser newUser, int page) {
		List<String> authorities = new ArrayList<String>();
		authorities.add("ROLE_USER");
		newUser.setAuthorities(authorities);
		return userRepository.insert(newUser);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		BleetUser user = userRepository.findByUsername(username);
		if (user == null){
			throw new UsernameNotFoundException("User " + username + " not found.");
		}
		User details = new UserWithId(user.getUsername(), user.getPassword(), true, true, true, true, authorities(user.getAuthorities()), user.getId());
		return details;
	}
	
	public List<GrantedAuthority> authorities(List<String> auths) {
		List<GrantedAuthority> result = new ArrayList<>();
		for (String auth : auths) {
			result.add(new SimpleGrantedAuthority(auth));
		}
		return result;
	}

	public BleetUser updateUser(BleetUser user) {
		return userRepository.update(user);
	}

	public BleetUser changeAdmin(String uid) {
		BleetUser user = userRepository.findOne(uid);
		List<String> auths = user.getAuthorities();
		boolean isAdmin = false;
		if(auths != null) {
			for (String auth : auths) {
				if (auth.equals("ROLE_ADMIN"))
					isAdmin = true;
			}
		}
		else {
			auths = new ArrayList<String>();
		}
		if(isAdmin)
			auths.remove("ROLE_ADMIN");
		else
			auths.add("ROLE_ADMIN");
		user.setAuthorities(auths);
		return userRepository.save(user);
	}
	
	public Page<BleetUser> findAllUsers(int p) {
		Pageable page = new PageRequest(p, PAGE_SIZE);
		return userRepository.findAll(page);
	}
	
}

