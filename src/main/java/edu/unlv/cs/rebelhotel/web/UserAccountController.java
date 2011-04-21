package edu.unlv.cs.rebelhotel.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import edu.unlv.cs.rebelhotel.domain.UserAccount;
import edu.unlv.cs.rebelhotel.email.UserEmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RooWebScaffold(path = "useraccounts", formBackingObject = UserAccount.class)
@RequestMapping("/useraccounts")
@Controller
public class UserAccountController {
	@Autowired
	UserEmailService userEmailService;
	
	@RequestMapping(method = RequestMethod.POST)
    public String create(@Valid UserAccount userAccount, BindingResult result, Model model, HttpServletRequest request) throws Exception {
        if (result.hasErrors()) {
            model.addAttribute("userAccount", userAccount);
            return "useraccounts/create";
        }
        userAccount.persist();
        // ADD THE CALL TO MAILER
         userEmailService.sendAdminComfirmation(userAccount);
        return "redirect:/useraccounts/" + encodeUrlPathSegment(userAccount.getId().toString(), request);
    }
}
