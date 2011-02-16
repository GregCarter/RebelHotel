package edu.unlv.cs.rebelhotel.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import edu.unlv.cs.rebelhotel.domain.Student;
import edu.unlv.cs.rebelhotel.domain.WorkEffort;
import edu.unlv.cs.rebelhotel.domain.WorkRequirement;

import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;

@RooWebScaffold(path = "workefforts", formBackingObject = WorkEffort.class)
@RequestMapping("/workefforts")
@Controller
public class WorkEffortController {
	
	// NOTE : the params string should not be equivalent to any of the fields in the form
	// otherwise the validator (?) will assume the params value is set to null (?) ... very annoying bug
	@RequestMapping(value = "/{sid}", params = "forstudent", method = RequestMethod.POST)
    public String createStudent(@PathVariable("sid") Long sid, @Valid WorkEffort workEffort, BindingResult result, Model model, HttpServletRequest request) {
		if (result.hasErrors() || (workEffort.getDuration().getStartDate().compareTo(workEffort.getDuration().getEndDate()) > 0)) {
            model.addAttribute("workEffort", workEffort);
            addDateTimeFormatPatterns(model);
            Student student = Student.findStudent(sid);
            Set<WorkRequirement> workRequirements = student.getWorkRequirement();
            model.addAttribute("studentworkrequirements", workRequirements);
            model.addAttribute("sid", sid);
            return "workefforts/createFromStudent";
        }
        workEffort.persist();
        Set<WorkEffort> workEfforts = workEffort.getStudent().getWorkEffort();
        workEfforts.add(workEffort);
        workEffort.getStudent().merge();
        return "redirect:/workefforts/" + encodeUrlPathSegment(workEffort.getId().toString(), request);
    }
	
	@RequestMapping(method = RequestMethod.POST)
    public String create(@Valid WorkEffort workEffort, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors() || (workEffort.getDuration().getStartDate().compareTo(workEffort.getDuration().getEndDate()) > 0)) {
            model.addAttribute("workEffort", workEffort);
            addDateTimeFormatPatterns(model);
            return "workefforts/create";
        }
        workEffort.persist();
        Set<WorkEffort> workEfforts = workEffort.getStudent().getWorkEffort();
        workEfforts.add(workEffort);
        workEffort.getStudent().merge();
        return "redirect:/workefforts/" + encodeUrlPathSegment(workEffort.getId().toString(), request);
    }
	
	@RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model model) {
        model.addAttribute("workEffort", new WorkEffort());
        addDateTimeFormatPatterns(model);
        List dependencies = new ArrayList();
        if (Student.countStudents() == 0) {
            dependencies.add(new String[]{"student", "students"});
        }
        model.addAttribute("dependencies", dependencies);
        return "workefforts/create";
    }
	
	@RequestMapping(value = "/{sid}", params = "forstudent", method = RequestMethod.GET)
    public String createStudentForm(@PathVariable("sid") Long sid, Model model) {
        model.addAttribute("workEffort", new WorkEffort());
        addDateTimeFormatPatterns(model);
        List dependencies = new ArrayList();
        if (Student.countStudents() == 0) {
            dependencies.add(new String[]{"student", "students"});
        }
        Student student = Student.findStudent(sid);
        Set<WorkRequirement> workRequirements = student.getWorkRequirement();
        model.addAttribute("studentworkrequirements", workRequirements);
        model.addAttribute("dependencies", dependencies);
        model.addAttribute("sid", sid);
        // TODO check if one is able to place the value of the student here without relying on the hidden form element
        // RESULT apparently it cannot be done
        return "workefforts/createFromStudent";
    }
	
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("workEffort", WorkEffort.findWorkEffort(id));
        addDateTimeFormatPatterns(model);
        return "workefforts/update";
    }
	
	void addDateTimeFormatPatterns(Model model) {
        model.addAttribute("workEffortDuration_startdate_date_format", DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()));
        model.addAttribute("workEffortDuration_enddate_date_format", DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()));
    }
}
