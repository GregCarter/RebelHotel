package edu.unlv.cs.rebelhotel.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;

import edu.unlv.cs.rebelhotel.domain.Student;
import edu.unlv.cs.rebelhotel.domain.UserAccount;
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
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RooWebScaffold(path = "students", formBackingObject = Student.class, exposeFinders=false)
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
	public String queryList(@Valid FormStudentQuery form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		studentQueryValidator.validate(form, result); // rather than assigning the validator to the student controller (like with the work effort controller), it should only apply to this method
		
		if (result.hasErrors()) {
			model.addAttribute("formStudentQuery", form);
			addQueryDateTimeFormatPatterns(model);
			return "students/query";
		}
		
		List<Student> students = studentQueryService.queryStudents(form);
		if (!form.getOutputCsv()) {
			String properties = studentQueryService.buildPropertiesString(form);
			String labels = studentQueryService.buildLabelsString(form);
			String maxLengths = studentQueryService.buildMaxLengthsString(form);
			
			model.addAttribute("formStudentQuery", form);
			model.addAttribute("students", students);
			model.addAttribute("tempColumnProperties", properties);
			model.addAttribute("tempColumnLabels", labels);
			model.addAttribute("tempColumnMaxLengths", maxLengths);
			return "students/queryList";
		}
		else {
			// code to generate CSV is called from here;
			// then the contents are placed into the temp file below before sent to the user as a download
			ServletContext servletContext = request.getSession().getServletContext();
			String filename = "thisfile.txt";
			
			File file = File.createTempFile("thisfile", ".txt");
			String contents = "blah blah";
			
			FileOutputStream fileWrite = new FileOutputStream(file);
			fileWrite.write(contents.getBytes());
			fileWrite.close();
			
			int filesize = (int) file.length();
			
			if (filesize > 0 ) {
				BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
				String mimetype = servletContext.getMimeType(filename);
				
				response.setBufferSize(filesize);
				response.setContentType(mimetype);
				response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("pragma", "no-cache");
				response.setDateHeader("Expires", 0);
				response.setContentLength(filesize);
				
				FileCopyUtils.copy(in, response.getOutputStream());
				in.close();
				
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
			else {
				response.setContentType("text/html");
				PrintWriter pw = response.getWriter();
				pw.println("<html>");
				pw.println("FAIL");
				pw.println("</html>");
			}
			
			//model.addAttribute("formStudentQuery", form);
			return null;
		}
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

    @RequestMapping(params = { "find=ByFirstNameEquals", "form" }, method = RequestMethod.GET)
    public String findStudentsByFirstNameEqualsForm(Model model) {
        return "students/findStudentsByFirstNameEquals";
    }
    
    @RequestMapping(params = "find=ByFirstNameEquals", method = RequestMethod.GET)
    public String findStudentsByFirstNameEquals(@RequestParam("firstName") String firstName, Model model) {
        model.addAttribute("students", Student.findStudentsByFirstNameEquals(firstName).getResultList());
        return "students/list";
    }
    
    @RequestMapping(params = { "find=ByFirstNameLike", "form" }, method = RequestMethod.GET)
    public String findStudentsByFirstNameLikeForm(Model model) {
        return "students/findStudentsByFirstNameLike";
    }
    
    @RequestMapping(params = "find=ByFirstNameLike", method = RequestMethod.GET)
    public String findStudentsByFirstNameLike(@RequestParam("firstName") String firstName, Model model) {
        model.addAttribute("students", Student.findStudentsByFirstNameLike(firstName).getResultList());
        return "students/list";
    }
    
    @RequestMapping(params = { "find=ByUserAccount", "form" }, method = RequestMethod.GET)
    public String findStudentsByUserAccountForm(Model model) {
        model.addAttribute("useraccounts", UserAccount.findAllUserAccounts());
        return "students/findStudentsByUserAccount";
    }
    
    @RequestMapping(params = "find=ByUserAccount", method = RequestMethod.GET)
    public String findStudentsByUserAccount(@RequestParam("userAccount") UserAccount userAccount, Model model) {
        model.addAttribute("students", Student.findStudentsByUserAccount(userAccount).getResultList());
        return "students/list";
    }
    
    @RequestMapping(params = { "find=ByUserIdEquals", "form" }, method = RequestMethod.GET)
    public String findStudentsByUserIdEqualsForm(Model model) {
        return "students/findStudentsByUserIdEquals";
    }
    
    @RequestMapping(params = "find=ByUserIdEquals", method = RequestMethod.GET)
    public String findStudentsByUserIdEquals(@RequestParam("userId") String userId, Model model) {
        model.addAttribute("students", Student.findStudentsByUserIdEquals(userId).getResultList());
        return "students/list";
    }
}
