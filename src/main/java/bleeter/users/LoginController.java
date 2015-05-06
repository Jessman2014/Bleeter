package bleeter.users;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	@Autowired
	private UserServices userServices;
	
	public BleetUser getUser(Principal p) {
		if(p == null) return null;		
		return userServices.findByUsername(p.getName());
	}
	
	@RequestMapping(value = "/home", method=RequestMethod.GET)
	public String login(Principal p, Model model) {
		model.addAttribute("user", getUser(p));
		return "app";		
	}
}