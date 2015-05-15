package bleeter.bleets;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
	@Autowired
	private BleetServices bleetServices;

	@Secured("ROLE_ADMIN")
	@RequestMapping("/bleets")
	@ResponseBody
	public Page<Bleet> getBleetsForAdmin(
			@RequestParam(required=false, defaultValue="") String username,
			@RequestParam(required=false, defaultValue="2999-01-01") @DateTimeFormat(pattern="yyyy-MM-dd") Date after,
			@RequestParam(required=false, defaultValue="1000-01-01") @DateTimeFormat(pattern="yyyy-MM-dd") Date before,
			@RequestParam(required = false, defaultValue="0") Integer page,
			@RequestParam(required = false, defaultValue="username") String sort,
			@RequestParam(required = false, defaultValue="asc") String order) {
		Sort s;
		if (order.equals("asc")) {
			s = new Sort(Direction.ASC, sort);
		}
		else {
			s = new Sort(Direction.DESC, sort);
		}
		if (page < 0)
			page = 0;
		return bleetServices.searchRangeAndUsername(before, after, username, page, s);
	}
	
	@RequestMapping("/bleets")
	@ResponseBody
	public Page<Bleet> getBleets(
			@RequestParam(required=false, defaultValue="") String username,
			@RequestParam(required=false, defaultValue="2999-01-01") @DateTimeFormat(pattern="yyyy-MM-dd") Date after,
			@RequestParam(required=false, defaultValue="1000-01-01") @DateTimeFormat(pattern="yyyy-MM-dd") Date before,
			@RequestParam(required = false, defaultValue="0") Integer page,
			@RequestParam(required = false, defaultValue="username") String sort,
			@RequestParam(required = false, defaultValue="asc") String order) {
		Sort s;
		if (order.equals("asc")) {
			s = new Sort(Direction.ASC, sort);
		}
		else {
			s = new Sort(Direction.DESC, sort);
		}
		if (page < 0)
			page = 0;
		return bleetServices.searchRangeAndUsernameWithNotPrivateNotBlocked(before, after, username, page, s);
	}
	
	
	
	@RequestMapping("users/{uid}")
	@ResponseBody
	@PreAuthorize(value = "principal.id == #uid")		
	public BleetUser getUser(@PathVariable String uid) {
		return userServices.findById(uid);
	}
	
	@RequestMapping(value = "users/{uid}", method = RequestMethod.PUT)
	@ResponseBody
	@PostAuthorize(value = "principal.id == #uid")
	public BleetUser updateUser(@PathVariable String uid,
			@RequestBody MultiValueMap<String, String> body) {
		String username = body.getFirst("username");
		String firstname = body.getFirst("firstname");
		String lastname = body.getFirst("lastname");
		String email = body.getFirst("email");
		String password = body.getFirst("password");
		BleetUser user = userServices.findByUsername(uid);
		user.setFirstName(firstname);
		user.setLastName(lastname);
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		return userServices.updateUser(user);
	}
	
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "users/{uid}/authorities", method = RequestMethod.PUT)
	@ResponseBody
	public BleetUser changeAdmin(@PathVariable String uid) {
		return userServices.changeAdmin(uid);
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/bleets/{bid}/block", method = RequestMethod.PUT)
	@ResponseBody
	public Bleet changeBlock(@PathVariable String bid) {
		return bleetServices.changeBlock(bid);
	}
	
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "users", method = RequestMethod.POST)
	@ResponseBody
	public Page<BleetUser> createUser(@RequestParam String username,
			@RequestParam String password,
			@RequestParam String firstname,
			@RequestParam String lastname,
			@RequestParam String email,
			@RequestParam(required = false, defaultValue="0") Integer page) {
		BleetUser newUser = new BleetUser.Builder().username(username).password(password)
				.firstName(firstname).lastName(lastname).email(email).build();
		return userServices.createUser(newUser, page);
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "users", method = RequestMethod.GET)
	@ResponseBody
	public Page<BleetUser> getUsers(
			@RequestParam(required = false, defaultValue="0") Integer page){			
		return userServices.findAllUsers(page);
	}

	@Secured("ROLE_USER")
	@RequestMapping("users/{uid}/bleets")
	@ResponseBody
	@PreAuthorize(value = "principal.id == #uid")
	public Page<Bleet> getUsersBleets(@PathVariable String uid,
			@RequestParam(required=false, defaultValue="2999-01-01") @DateTimeFormat(pattern="yyyy-MM-dd") Date after,
			@RequestParam(required=false, defaultValue="1000-01-01") @DateTimeFormat(pattern="yyyy-MM-dd") Date before,
			@RequestParam(required = false, defaultValue="0") Integer page,
			@RequestParam(required = false, defaultValue="username") String sort,
			@RequestParam(required = false, defaultValue="asc") String order) {
		Sort s;
		if (order.equals("asc")) {
			s = new Sort(Direction.ASC, sort);
		}
		else {
			s = new Sort(Direction.DESC, sort);
		}
		if (page < 0)
			page = 0;
		return bleetServices.searchRangeWithUid(before, after, uid, page, s);
	}
	
	@Secured("ROLE_USER")
	@RequestMapping("users/{uid}/bleets/{bid}")
	@ResponseBody
	@PreAuthorize(value = "principal.id == #uid")		
	public Bleet getBleet(@PathVariable String uid, @PathVariable String bid) {
		return bleetServices.findByBleetId(uid, bid);
	}
	
	@Secured("ROLE_USER")
	@RequestMapping(value = "users/{uid}/bleets/{bid}", method = RequestMethod.DELETE)
	@ResponseBody
	@PreAuthorize("principal.id == #uid")		
	public Page<Bleet> deleteBleet(@PathVariable String uid, @PathVariable String bid) {	
		return bleetServices.deleteBleet(uid, bid);
	}	
	
	@Secured("ROLE_USER")
	@RequestMapping(value = "users/{uid}/bleets", method = RequestMethod.POST)
	@ResponseBody
	@PostAuthorize(value = "principal.id == #uid")		
	public Page<Bleet> createBleet(@PathVariable String uid,
			@RequestParam String bleet,
			@RequestParam Boolean privatecomment) {
		BleetUser user = userServices.findById(uid);
		Bleet newBleet = new Bleet.Builder().bleet(bleet).privateComment(privatecomment)
				.uid(uid).username(user.getUsername()).sentiment("").timestamp(new Date())
				.confidence(0).build();
		return bleetServices.addBleet(uid, newBleet);
	}
	
	@Secured("ROLE_USER")
	@RequestMapping(value = "users/{uid}/bleets/{bid}", method = RequestMethod.PUT)
	@ResponseBody
	@PostAuthorize(value = "principal.id == #uid")
	public Page<Bleet> updateBleet(@PathVariable String uid, @PathVariable String bid,
			@RequestParam String bleet, @RequestParam Boolean privatecomment){
		return bleetServices.updateBleet(uid, bid, bleet, privatecomment);
	}
	
}
