package bleeter.users;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import bleeter.bleets.Bleet;

@Service
public class UserServices implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	public BleetUser findById(String uid) {
		return userRepository.findOne(uid);
	}

	public BleetUser findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public BleetUser createUser(BleetUser newUser) {
		List<String> authorities = new ArrayList<String>();
		authorities.add("ROLE_USER");
		return userRepository.insert(newUser);
	}

	public Page<BleetUser> findAll(int min, int max) {
		return userRepository.findAll(new PageRequest(min, max));
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
	
	public List<Bleet> addBleet(String uid, Bleet newBleet) {
		BleetUser user = userRepository.findByUsername(uid);
		List<Bleet> bleets = user.getBleets();
		if (bleets == null){
			bleets = new ArrayList<Bleet>();
		}
		bleets.add(newBleet);
		user.setBleets(bleets);
		userRepository.update(user);
		return bleets;
	}

	public List<Bleet> deleteBleet(String uid, String bid) {
		BleetUser user = userRepository.findByUsername(uid);
		List<Bleet> bleets = user.getBleets();
		if (bleets != null){
			for (Bleet bleet : bleets) {
				if (bleet.getId().equals(bid)) {
					bleets.remove(bleet);
					break;
				}
			}
		}
		userRepository.update(user);
		return bleets;
	}

	public Bleet findByBleetId(String uid, String bid) {
		return userRepository.findByBleetId(uid, bid);
	}

	public List<Bleet> updateBleet(String uid, String bid, String bleet,
			Boolean privatecomment) {
		Bleet b = userRepository.findByBleetId(uid, bid);
		b.setBleet(bleet);
		b.setPrivateComment(privatecomment);
		deleteBleet(uid, bid);
		return addBleet(uid, b);
	}

	public List<Bleet> findAllBleets() {
		List<BleetUser> users = userRepository.findAll();
		List<Bleet> bleets = new ArrayList<Bleet>();
		for (BleetUser user : users) {
			bleets.addAll(user.getBleets());
		}
		return bleets;
	}

	public List<Bleet> findUsersBleets(String uid) {
		return userRepository.findByUsername(uid)
				.getBleets();
	}

	public BleetUser updateUser(BleetUser user) {
		return userRepository.update(user);
	}

	public BleetUser makeAdmin(String uid) {
		// TODO Auto-generated method stub
		return null;
	}

	public BleetUser changeBlock(String bid, Boolean block) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}

