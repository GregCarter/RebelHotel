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

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;

@RooWebScaffold(path = "workefforts", formBackingObject = WorkEffort.class)
@RequestMapping("/workefforts")
@Controller
public class WorkEffortController {
	@RequestMapping(method = RequestMethod.POST)
    public String create(@Valid WorkEffort workEffort, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("workEffort", workEffort);
            return "workefforts/create";
        }
        workEffort.persist();
        Set<WorkEffort> workEfforts = workEffort.getStudent().getWorkEffort();
        workEfforts.add(workEffort);
        workEffort.getStudent().merge();
        return "redirect:/workefforts/" + encodeUrlPathSegment(workEffort.getId().toString(), request);
    }
	
	@RequestMapping(value = "/{sid}", params = "student", method = RequestMethod.GET)
    public String createFormFromStudent(@PathVariable("sid") Long sid, Model model) {
        model.addAttribute("workEffort", new WorkEffort());
        List dependencies = new ArrayList();
        if (Student.countStudents() == 0) {
            dependencies.add(new String[]{"student", "students"});
        }
        Student student = Student.findStudent(sid);
        Set<WorkRequirement> workRequirements = student.getWorkRequirements();
        model.addAttribute("studentworkrequirements", workRequirements);
        model.addAttribute("student", sid);
        model.addAttribute("dependencies", dependencies);
        return "workefforts/createFromStudent";
    }
}
