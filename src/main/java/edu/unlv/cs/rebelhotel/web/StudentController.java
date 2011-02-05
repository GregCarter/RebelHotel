package edu.unlv.cs.rebelhotel.web;

import edu.unlv.cs.rebelhotel.domain.Student;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RooWebScaffold(path = "students", formBackingObject = Student.class)
@RequestMapping("/students")
@Controller
public class StudentController {
	@RequestMapping(params = "search", method = RequestMethod.GET)
	public String searchForm(Model model) {
		model.addAttribute("student", new Student());
		return "students/search";
	}
}
