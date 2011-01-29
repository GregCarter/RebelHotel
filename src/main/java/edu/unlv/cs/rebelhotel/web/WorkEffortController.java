package edu.unlv.cs.rebelhotel.web;

import edu.unlv.cs.rebelhotel.domain.WorkEffort;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "workefforts", formBackingObject = WorkEffort.class)
@RequestMapping("/workefforts")
@Controller
public class WorkEffortController {
}
