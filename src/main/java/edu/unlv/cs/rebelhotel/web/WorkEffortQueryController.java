package edu.unlv.cs.rebelhotel.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.unlv.cs.rebelhotel.form.FormWorkEffortQuery;
import edu.unlv.cs.rebelhotel.service.WorkEffortQueryService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import edu.unlv.cs.rebelhotel.domain.Major;
import edu.unlv.cs.rebelhotel.domain.Student;
import edu.unlv.cs.rebelhotel.domain.WorkEffort;
import edu.unlv.cs.rebelhotel.domain.enums.Validation;
import edu.unlv.cs.rebelhotel.service.StudentQueryService;
import edu.unlv.cs.rebelhotel.service.UserInformation;
import edu.unlv.cs.rebelhotel.validators.WorkEffortQueryValidator;
import edu.unlv.cs.rebelhotel.validators.WorkEffortValidator;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/workeffortquery/**")
@Controller
public class WorkEffortQueryController {

	@Autowired
	WorkEffortQueryService workeffortqueryservice;

	@Autowired
	WorkEffortQueryValidator workeffortqueryvalidator;
	


	void setWorkEffortQueryService(WorkEffortQueryService workeffortqueryservice) {
		this.workeffortqueryservice = workeffortqueryservice;
	}
	
	void setWorkEffortQueryValidator(WorkEffortQueryValidator workeffortqueryvalidator){
		this.workeffortqueryvalidator = workeffortqueryvalidator;
	}

	@RequestMapping(method = RequestMethod.POST, value = "{id}")
	public void get(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
	}

	
	
	@RequestMapping(params = "query", method = RequestMethod.POST)
	public String queryList(@Valid FormWorkEffortQuery form,
			BindingResult result, Model model, HttpServletRequest request) {
		workeffortqueryvalidator.validate(form,result);
		if (result.hasErrors()) {
			model.addAttribute("formworkeffortquery", form);
			addDateTimeFormatPatterns(model);
			return "workeffortquery/findWorkEfforts";
		}
		

		List<WorkEffort> workefforts = workeffortqueryservice
				.queryWorkEfforts(form);
		String properties = workeffortqueryservice.buildPropertiesString();
		String labels = workeffortqueryservice.buildLabelsString();
		String maxLengths = workeffortqueryservice.buildMaxLengthsString();

		
		model.addAttribute("formworkeffortquery", form);
		addDateTimeFormatPatterns(model);
		model.addAttribute("workefforts", workefforts);
		model.addAttribute("tempColumnProperties", properties);
		model.addAttribute("tempColumnLabels", labels);
		model.addAttribute("tempColumnMaxLengths", maxLengths);
		return "workeffortquery/queryList";
	}

	@RequestMapping(params = { "query", "form" }, method = RequestMethod.GET)
	public String query(Model model) {
		FormWorkEffortQuery fweq = new FormWorkEffortQuery();
		model.addAttribute("formworkeffortquery", fweq);
		addDateTimeFormatPatterns(model);
		return "workeffortquery/findWorkEfforts";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@Valid FormWorkEffortQuery fweq, BindingResult result, Model model) {

		model.addAttribute("workefforts",
				workeffortqueryservice.queryWorkEfforts(fweq));

		return "workefforts/list";
	}

	void addDateTimeFormatPatterns(Model model) {
		model.addAttribute(
				"formWorkEffortQuery_startdate_date_format",
				DateTimeFormat.patternForStyle("S-",
						LocaleContextHolder.getLocale()));
		model.addAttribute(
				"formWorkEffortQuery_enddate_date_format",
				DateTimeFormat.patternForStyle("S-",
						LocaleContextHolder.getLocale()));
	}

	@ModelAttribute("validations")
	public Collection<Validation> populateValidations() {
		return Arrays.asList(Validation.class.getEnumConstants());
	}

}
