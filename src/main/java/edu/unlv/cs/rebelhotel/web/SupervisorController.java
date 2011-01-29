package edu.unlv.cs.rebelhotel.web;

import edu.unlv.cs.rebelhotel.domain.Supervisor;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "supervisors", formBackingObject = Supervisor.class)
@RequestMapping("/supervisors")
@Controller
public class SupervisorController {
}
