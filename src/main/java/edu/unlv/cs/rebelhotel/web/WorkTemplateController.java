package edu.unlv.cs.rebelhotel.web;

import edu.unlv.cs.rebelhotel.domain.WorkTemplate;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "worktemplates", formBackingObject = WorkTemplate.class)
@RequestMapping("/worktemplates")
@Controller
public class WorkTemplateController {
}
