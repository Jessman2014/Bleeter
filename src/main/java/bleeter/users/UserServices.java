package bleeter.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

public class UserServices implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		BleetUser user = userRepository.findByUsername(username);
		if (user == null){
			throw new UsernameNotFoundException("User " + username + " not found.");
		}
		User details = new UserWithId(user.getUsername(), user.getPassword(), true, true, true, true, authorities(user.getAuthorities()), user.getId());
		return details;
	}
}

