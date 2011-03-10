package edu.unlv.cs.rebelhotel.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.unlv.cs.rebelhotel.form.FormWorkEffortForStudent;

@Component
public class WorkEffortForStudentValidator implements Validator {
	@Autowired
	private WorkEffortValidator workEffortValidator;
	
	public void setWorkEffortValidator(WorkEffortValidator workEffortValidator) {
		this.workEffortValidator = workEffortValidator;
	}
	
	public boolean supports(Class clazz) {
		return FormWorkEffortForStudent.class.isAssignableFrom(clazz);
	}
	
	public void validate(Object target, Errors errors) {
		FormWorkEffortForStudent formObject = (FormWorkEffortForStudent) target;
		try {
			errors.pushNestedPath("workEffort");
			workEffortValidator.validate(formObject.getWorkEffort(), errors);
		}
		finally {
			errors.popNestedPath();
		}
	}
}