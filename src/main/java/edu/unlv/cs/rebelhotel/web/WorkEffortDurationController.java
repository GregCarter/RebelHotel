package edu.unlv.cs.rebelhotel.web;

import edu.unlv.cs.rebelhotel.domain.WorkEffortDuration;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "workeffortdurations", formBackingObject = WorkEffortDuration.class)
@RequestMapping("/workeffortdurations")
@Controller
public class WorkEffortDurationController {
}
