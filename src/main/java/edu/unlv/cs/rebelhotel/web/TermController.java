package edu.unlv.cs.rebelhotel.web;

import edu.unlv.cs.rebelhotel.domain.Term;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "terms", formBackingObject = Term.class)
@RequestMapping("/terms")
@Controller
public class TermController {
}
