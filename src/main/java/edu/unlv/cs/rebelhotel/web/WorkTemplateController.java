package edu.unlv.cs.rebelhotel.web;

import java.util.Collection;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import edu.unlv.cs.rebelhotel.domain.Student;
import edu.unlv.cs.rebelhotel.domain.WorkRequirement;
import edu.unlv.cs.rebelhotel.domain.WorkTemplate;
import edu.unlv.cs.rebelhotel.form.FormWorkRequirement;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RooWebScaffold(path = "worktemplates", formBackingObject = WorkTemplate.class)
@RequestMapping("/worktemplates")
@Controller
public class WorkTemplateController {
	@RequestMapping(value = "/{sid}", params = "requirement", method = RequestMethod.GET)
    public String createRequirementForm(@PathVariable("sid") Long sid, Model model) {
        model.addAttribute("formWorkRequirement", new FormWorkRequirement());
        model.addAttribute("sid", sid);
        return "worktemplates/createRequirement";
    }
	
	@RequestMapping(value = "/{sid}", params = "requirement", method = RequestMethod.POST)
    public String createRequirement(@PathVariable("sid") Long sid, @ModelAttribute("formWorkRequirement") FormWorkRequirement formWorkRequirement, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors() || formWorkRequirement.getWorkTemplate() == null) {
            model.addAttribute("formWorkRequirement", formWorkRequirement);
            model.addAttribute("sid", sid);
            return "worktemplates/createRequirement";
        }
        WorkTemplate workTemplate = formWorkRequirement.getWorkTemplate();
        Student student = Student.findStudent(sid);
        WorkRequirement workRequirement = WorkRequirement.fromWorkTemplate(workTemplate, student);
        workRequirement.persist();
        Set<WorkRequirement> workRequirements = student.getWorkRequirements();
        workRequirements.add(workRequirement);
        student.merge();
        return "redirect:/workrequirements/" + encodeUrlPathSegment(workRequirement.getId().toString(), request);
    }
	
	@ModelAttribute("templates")
    public Collection<WorkTemplate> populateTemplates() {
        return WorkTemplate.findAllWorkTemplates();
    }
}
