package bleeter.bleets;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
public class BleetController {
	@Autowired
	private UserServices userServices;

	@RequestMapping("/bleets")
	@ResponseBody
	public List<Bleet> getBleets() {
		return userServices.findAllBleets();
	}
	
	@RequestMapping(value = "users/{uid}", method = RequestMethod.PUT)
	@ResponseBody
	@PostAuthorize(value = "principal.id == #uid")
	public BleetUser updateUser(@PathVariable String uid, @RequestParam String firstname,
			@RequestParam String lastname, @RequestParam String username, 
			@RequestParam String email) {
		BleetUser user = userServices.findByUsername(uid);
		user.setFirstName(firstname);
		user.setLastName(lastname);
		user.setUsername(username);
		user.setEmail(email);
		return userServices.updateUser(user);
	}
	
	@RequestMapping("users/{uid}")
	@ResponseBody
	@PreAuthorize(value = "principal.id == #uid")		
	public BleetUser getUser(@PathVariable String uid, 
			@RequestParam(required=false, defaultValue="username") String order) {
		return userServices.findById(uid, order);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "users/{uid}/authorities", method = RequestMethod.PUT)
	@ResponseBody
	public BleetUser makeAdmin(@PathVariable String uid) {
		return userServices.makeAdmin(uid);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/bleets/{bid}/block", method = RequestMethod.PUT)
	@ResponseBody
	public BleetUser changeBlock(@PathVariable String bid,
			@RequestParam Boolean block) {
		return userServices.changeBlock(bid, block);
	}
	
	/*@Secured("ROLE_ADMIN")
	@RequestMapping(value = "users/{uid}/authorities", method = RequestMethod.DELETE)
	@ResponseBody
	public BleetUser removeAdmin(@PathVariable String uid) {
		return userServices.removeAdmin(uid);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/bleets")
	@ResponseBody
	public List<Bleet> searchBleets(@RequestParam(required=false, defaultValue="") String username,
			@RequestParam(required=false, defaultValue="01/01/2999") Date timestamp) {
		return userServices.searchBleets(username, timestamp);
	}*/

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "users", method = RequestMethod.POST)
	@ResponseBody
	public BleetUser createUser(@RequestParam String username,
			@RequestParam String password,
			@RequestParam String firstname,
			@RequestParam String lastname,
			@RequestParam String email) {
		BleetUser newUser = new BleetUser.Builder().username(username).password(password)
				.firstName(firstname).lastName(lastname).email(email).build();
		return userServices.createUser(newUser);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "users/{page}", method = RequestMethod.GET)
	@ResponseBody
	public Page<BleetUser> getUsers(@PathVariable Integer page
			) {
		return userServices.findAll(page*2, page*2+9);
	}

	@Secured("ROLE_USER")
	@RequestMapping("users/{uid}/bleets")
	@ResponseBody
	@PreAuthorize(value = "principal.id == #uid")
	public List<Bleet> getUsersBleets(@PathVariable String uid) {
		return userServices.findUsersBleets(uid);
	}
	
	@Secured("ROLE_USER")
	@RequestMapping("users/{uid}/bleets/{bid}")
	@ResponseBody
	@PreAuthorize(value = "principal.id == #uid")		
	public Bleet getBleet(@PathVariable String uid, @PathVariable String bid) {
		return userServices.findByBleetId(uid, bid);
	}
	
	@Secured("ROLE_USER")
	@RequestMapping(value = "users/{uid}/bleets/{bid}", method = RequestMethod.DELETE)
	@ResponseBody
	@PreAuthorize("principal.id == #uid")		
	public List<Bleet> deleteBleet(@PathVariable String uid, @PathVariable String bid) {	
		return userServices.deleteBleet(uid, bid);
	}	
	
	@Secured("ROLE_USER")
	@RequestMapping(value = "users/{uid}/bleets", method = RequestMethod.POST)
	@ResponseBody
	@PostAuthorize(value = "principal.id == #uid")		
	public List<Bleet> createBleet(@PathVariable String uid,
			@RequestParam String bleet,
			@RequestParam Boolean privatecomment) {
		Bleet newBleet = new Bleet.Builder().bleet(bleet).privateComment(privatecomment)
				.uid(uid).build();
		return userServices.addBleet(uid, newBleet);
	}
	
	@Secured("ROLE_USER")
	@RequestMapping(value = "users/{uid}/bleets/{bid}", method = RequestMethod.PUT)
	@ResponseBody
	@PostAuthorize(value = "principal.id == #uid")
	public List<Bleet> updateBleet(@PathVariable String uid, @PathVariable String bid,
			@RequestParam String bleet, @RequestParam Boolean privatecomment){
		return userServices.updateBleet(uid, bid, bleet, privatecomment);
	}
	
}
