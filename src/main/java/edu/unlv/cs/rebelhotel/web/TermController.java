package edu.unlv.cs.rebelhotel.web;

import java.util.List;

import edu.unlv.cs.rebelhotel.domain.Term;
import edu.unlv.cs.rebelhotel.service.TermService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "terms", formBackingObject = Term.class)
@RequestMapping("/terms")
@Controller
public class TermController {
	@Autowired
	TermService termService;
	
	@RequestMapping(params = "generate")
	public String generateTerms(Model model) {
		List<Term> result = termService.generateCurrentTerms();
		model.addAttribute("terms_num", result.size());
		model.addAttribute("terms", result);
		return "terms/generate";
	}
}
