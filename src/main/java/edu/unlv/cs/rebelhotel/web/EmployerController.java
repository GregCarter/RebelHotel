package edu.unlv.cs.rebelhotel.web;

import edu.unlv.cs.rebelhotel.domain.Employer;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "employers", formBackingObject = Employer.class)
@RequestMapping("/employers")
@Controller
public class EmployerController {
}
