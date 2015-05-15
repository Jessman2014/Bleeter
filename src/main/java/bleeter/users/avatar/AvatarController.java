package bleeter.users.avatar;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import bleeter.users.BleetUser;
import bleeter.users.UserServices;

@Controller
@RequestMapping("/users")
public class AvatarController {
	@Autowired
	private UserServices userServices;
	
	@Autowired
	private AvatarServices avatarServices;
			
	@RequestMapping(value="/{uid}/avatar", method=RequestMethod.GET)
	@ResponseBody
	@PreAuthorize("principal.id == #uid")
	public byte[] getAvatar(@PathVariable String uid) throws IOException {		
		return avatarServices.getImageData(uid);	
	}
		
	@RequestMapping(value="/{uid}/avatar", method=RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("principal.id == #uid")
	public BleetUser uploadAvatar(@PathVariable String uid, @RequestParam("avatar") MultipartFile file) throws IllegalStateException, IOException {
		BleetUser user = userServices.findById(uid);
		if(!file.isEmpty()) {
			return avatarServices.uploadAvatar(user, file);
		} else {
			return user;
		}
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/{uid}/avatar/admin", method=RequestMethod.POST)
	@ResponseBody
	public BleetUser uploadAvatarForAdmin(@PathVariable String uid, @RequestParam("avatar") MultipartFile file) throws IllegalStateException, IOException {
		BleetUser user = userServices.findById(uid);
		if(!file.isEmpty()) {
			return avatarServices.uploadAvatar(user, file);
		} else {
			return user;
		}
	}
}