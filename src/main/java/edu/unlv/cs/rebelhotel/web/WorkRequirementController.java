package edu.unlv.cs.rebelhotel.web;

import edu.unlv.cs.rebelhotel.domain.WorkRequirement;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "workrequirements", formBackingObject = WorkRequirement.class)
@RequestMapping("/workrequirements")
@Controller
public class WorkRequirementController {
}
