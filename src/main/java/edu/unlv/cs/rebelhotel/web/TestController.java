package edu.unlv.cs.rebelhotel.web;

import java.util.Collection;

import edu.unlv.cs.rebelhotel.domain.Student;
import edu.unlv.cs.rebelhotel.domain.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.unlv.cs.rebelhotel.service.SpringApplicationContext;
import edu.unlv.cs.rebelhotel.service.UserInformation;

@RooWebScaffold(path = "test", formBackingObject = Test.class)
@RequestMapping("/test")
@Controller
public class TestController {
	@Autowired
	UserInformation userInformation;
	
	@RequestMapping(params = "test2", method = RequestMethod.GET)
	public String Test2(Model model) {
		Student student = userInformation.getStudent();
		if (student == null) {
			model.addAttribute("str", "Student was null");
		}
		else {
			model.addAttribute("str", student.getFirstName()+" "+student.getLastName());
		}
		
		return "test/test2";
	}
	
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
		
		Collection<GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		if (authorities.contains("ROLE_USER")) {
			// All students should have the ROLE_USER role and should also have usernames consisting of 10  digit numbers
		}
		String auths = "";
		for (GrantedAuthority authority: authorities) {
			auths += authority + "<br />";
		}
		
		model.addAttribute("Author", auths);
		
		return "test/test";
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String test2(@PathVariable("id") Long id, Model model) {
		//ServiceTest st = new ServiceTest();
        //model.addAttribute("str", st.get1());
		model.addAttribute("str", "Failure (intentional)");
        return "test/awesome";
    }
}
