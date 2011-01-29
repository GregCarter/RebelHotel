package edu.unlv.cs.rebelhotel.web;

import edu.unlv.cs.rebelhotel.domain.UserAccount;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "useraccounts", formBackingObject = UserAccount.class)
@RequestMapping("/useraccounts")
@Controller
public class UserAccountController {
}
