package edu.unlv.cs.rebelhotel.web;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

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
	@RequestMapping(params = "query", method = RequestMethod.GET)
	public String query(Model model) {
		Session session = (Session) Student.entityManager().unwrap(Session.class);
		session.beginTransaction();
		Criteria search = session.createCriteria(Student.class);
		//List students = session.createCriteria(Student.class)
		//.add(Restrictions.like("firstName", "Sam%"))
		//.list();
		search.add(Restrictions.like("firstName", "Sam%"));
		List students = search.list();
		
		model.addAttribute("str", "Here is a test string for you!");
		model.addAttribute("students", students);
		return "students/query";
	}
}
