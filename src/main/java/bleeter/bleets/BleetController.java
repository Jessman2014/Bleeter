package bleeter.bleets;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bleeter.users.BleetUser;
import bleeter.users.UserServices;

@Controller
@RequestMapping("/users")
public class BleetController {
	@Autowired
	private UserServices userServices;

	@Secured("ROLE_USER")
	@RequestMapping("/{uid}")
	@ResponseBody
	@PreAuthorize(value = "principal.id == #uid")		
	public BleetUser getUser(@PathVariable String uid) {
		return userServices.findById(uid);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public BleetUser createUser(@RequestParam String username,
			@RequestParam String password,
			@RequestParam String firstname,
			@RequestParam String lastname,
			@RequestParam String email) {
		BleetUser newUser = new BleetUser(null, firstname, lastname,
				username, email, null, null, null, null, password);
		return userServices.createUser(newUser);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public List<BleetUser> getUsers() {
		return userServices.findAll();
	}

	@Secured("ROLE_USER")
	@RequestMapping("/{uid}/images/{bid}")
	@ResponseBody
	@PreAuthorize(value = "principal.id == #uid")		
	public Bleet getBleet(@PathVariable String uid, @PathVariable String bid) {
		return userServices.findByBleetId(uid, bid);
	}
	
	@Secured("ROLE_USER")
	@RequestMapping(value = "/{uid}/bleets/{bid}", method = RequestMethod.DELETE)
	@ResponseBody
	@PreAuthorize("principal.id == #uid")		
	public List<Bleet> deleteBleet(@PathVariable String uid, @PathVariable String bid) {	
		return userServices.deleteBleet(bid);
	}	
	
	@Secured("ROLE_USER")
	@RequestMapping(value = "/{uid}/bleets", method = RequestMethod.POST)
	@ResponseBody
	@PostAuthorize(value = "principal.id == #uid")		
	public List<Bleet> createBleet(@PathVariable String uid,
			@RequestParam String url,
			@RequestParam String comment) {
		return userServices.addBleet(uid, url, comment);
	}
	
}
