package edu.unlv.cs.rebelhotel.web;

import edu.unlv.cs.rebelhotel.domain.CatalogRequirement;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "catalogrequirements", formBackingObject = CatalogRequirement.class)
@RequestMapping("/catalogrequirements")
@Controller
public class CatalogRequirementController {
}
