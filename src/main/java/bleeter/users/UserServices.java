package bleeter.users;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserServices implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	public BleetUser findById(String uid) {
		return userRepository.findOne(uid);
	}

	public BleetUser findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public BleetUser createUser(String username, String password) {
		List<String> authorities = new ArrayList<String>();
		authorities.add("ROLE_USER");
		BleetUser newUser = new BleetUser((String) null, username, password, authorities, new ArrayList<Image>());
		return userRepository.insert(newUser);
	}

	public Bleet findByBleetId(String uid, String bid) {
		return userRepository.findByBleetId(uid, bid);
	}
	
	public List<BleetUser> findAll() {
		return userRepository.findAll();
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
}

