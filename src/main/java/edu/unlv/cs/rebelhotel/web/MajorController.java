package edu.unlv.cs.rebelhotel.web;

import edu.unlv.cs.rebelhotel.domain.Major;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "majors", formBackingObject = Major.class)
@RequestMapping("/majors")
@Controller
public class MajorController {
}
