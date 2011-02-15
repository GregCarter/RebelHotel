package edu.unlv.cs.rebelhotel.web;

import edu.unlv.cs.rebelhotel.domain.ViewProgress;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "viewprogresses", formBackingObject = ViewProgress.class)
@RequestMapping("/viewprogresses")
@Controller
public class ViewProgressController {
}
