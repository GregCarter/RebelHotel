package edu.unlv.cs.rebelhotel.web;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;

import edu.unlv.cs.rebelhotel.domain.Student;
import edu.unlv.cs.rebelhotel.domain.enums.Departments;
import edu.unlv.cs.rebelhotel.domain.enums.Semester;
import edu.unlv.cs.rebelhotel.form.FormStudentQuery;
import edu.unlv.cs.rebelhotel.service.StudentQueryService;
import edu.unlv.cs.rebelhotel.validators.StudentQueryValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RooWebScaffold(path = "students", formBackingObject = Student.class)
@RequestMapping("/students")
@Controller
public class StudentController {
	@Autowired
	StudentQueryValidator studentQueryValidator;
	
	@Autowired
	StudentQueryService studentQueryService;
	
	void setStudentQueryValidator(StudentQueryValidator studentQueryValidator) {
		this.studentQueryValidator = studentQueryValidator;
	}
	
	void setStudentQueryService(StudentQueryService studentQueryService) {
		this.studentQueryService = studentQueryService;
	}
	
	void addDateTimeFormatPatterns(Model model) {
        model.addAttribute("student_lastmodified_date_format", DateTimeFormat.patternForStyle("SF", LocaleContextHolder.getLocale()));
    }
	
	void addQueryDateTimeFormatPatterns(Model model) {
        model.addAttribute("student_query_date_format", DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()));
    }
	
	@ModelAttribute("query_semesters")
    public Collection<Semester> populateQuerySemesters() {
        return Arrays.asList(Semester.class.getEnumConstants());
    }
	
	@ModelAttribute("departments")
    public Collection<Departments> populateDepartments() {
        return Arrays.asList(Departments.class.getEnumConstants());
    }
	
	@RequestMapping(params = "query", method = RequestMethod.POST)
	public String queryList(@Valid FormStudentQuery form, BindingResult result, Model model, HttpServletRequest request) {
		studentQueryValidator.validate(form, result); // rather than assigning the validator to the student controller (like with the work effort controller), it should only apply to this method
		
		if (result.hasErrors()) {
			model.addAttribute("formStudentQuery", form);
			addQueryDateTimeFormatPatterns(model);
			return "students/query";
		}
		
		List<Student> students = studentQueryService.queryStudents(form);
		
		model.addAttribute("str", "Here is a test string for you!");
		model.addAttribute("students", students);
		return "students/queryList";
	}
	
	@RequestMapping(params = "query", method = RequestMethod.GET)
	public String query(Model model) {
		FormStudentQuery fsq = new FormStudentQuery();
		fsq.setLastModifiedStart(new Date());
		fsq.setLastModifiedEnd(new Date());
		model.addAttribute("formStudentQuery", fsq);
		addQueryDateTimeFormatPatterns(model);
		return "students/query";
	}
}
