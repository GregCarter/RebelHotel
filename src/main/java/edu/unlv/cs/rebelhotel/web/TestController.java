package edu.unlv.cs.rebelhotel.web;

import edu.unlv.cs.rebelhotel.domain.Test;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RooWebScaffold(path = "test", formBackingObject = Test.class)
@RequestMapping("/test")
@Controller
public class TestController {
	@RequestMapping(params = "param", method = RequestMethod.GET)
	public String randomTest(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		}
		else {
			username = principal.toString();
		}
		username = username + SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("str", username);
		return "test/test";
	}
}
