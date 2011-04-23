package edu.unlv.cs.rebelhotel.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.unlv.cs.rebelhotel.domain.WorkEffort;

// Make it a component so that Spring creates a bean of it that can be auto injected into instances that need it
@Component
public class WorkEffortValidator implements Validator {
	//Auto wiring tells Spring to automatically inject this dependency based on the type of variable
	@Autowired
	private WorkEffortDurationValidator workEffortDurationValidator;
	
	// A mutator / setter method such as this is necessary for "set" based dependency injection
	public void setWorkEffortDurationValidator(WorkEffortDurationValidator workEffortDurationValidator) {
		this.workEffortDurationValidator = workEffortDurationValidator;
	}
	
	public boolean supports(Class clazz) {
		return WorkEffort.class.isAssignableFrom(clazz);
	}
	
	public void validate(Object target, Errors errors) {
		WorkEffort job = (WorkEffort) target;
		try {
			errors.pushNestedPath("duration");
			workEffortDurationValidator.validate(job.getDuration(), errors);
		}
		finally {
			errors.popNestedPath();
		}
	}
}