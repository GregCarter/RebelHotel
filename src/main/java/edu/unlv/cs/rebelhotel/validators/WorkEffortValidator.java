package edu.unlv.cs.rebelhotel.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.unlv.cs.rebelhotel.domain.WorkEffort;
import edu.unlv.cs.rebelhotel.domain.WorkEffortDuration;

@Component
public class WorkEffortValidator implements Validator {
	@Autowired
	private WorkEffortDurationValidator workEffortDurationValidator;
	
	public void setWorkEffortDurationValidator(WorkEffortDurationValidator workEffortDurationValidator) {
		if (workEffortDurationValidator == null) {
			throw new IllegalArgumentException("The Validator parameter is required and must not be null");
		}
		if (!workEffortDurationValidator.supports(WorkEffortDuration.class)) {
			throw new IllegalArgumentException("The given Validator must support the validation of WorkEffortDuration instances");
		}
		this.workEffortDurationValidator = workEffortDurationValidator;
	}
	
	public boolean supports(Class clazz) {
		return WorkEffort.class.isAssignableFrom(clazz);
	}
	
	public void validate(Object target, Errors errors) {
		WorkEffort we = (WorkEffort) target;
		try {
			errors.pushNestedPath("duration");
			workEffortDurationValidator.validate(we.getDuration(), errors);
		}
		finally {
			errors.popNestedPath();
		}
	}
}