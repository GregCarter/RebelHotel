package edu.unlv.cs.rebelhotel.web;

import edu.unlv.cs.rebelhotel.domain.Student;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "students", formBackingObject = Student.class)
@RequestMapping("/students")
@Controller
public class StudentController {
}
