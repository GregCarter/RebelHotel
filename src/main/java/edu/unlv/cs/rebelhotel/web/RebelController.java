package edu.unlv.cs.rebelhotel.web;

import edu.unlv.cs.rebelhotel.domain.Rebel;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "rebels", formBackingObject = Rebel.class)
@RequestMapping("/rebels")
@Controller
public class RebelController {
}
