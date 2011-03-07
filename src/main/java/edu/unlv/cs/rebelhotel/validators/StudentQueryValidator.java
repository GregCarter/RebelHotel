package edu.unlv.cs.rebelhotel.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.unlv.cs.rebelhotel.form.FormStudentQuery;

@Component
public class StudentQueryValidator implements Validator {
	@Autowired
	private TermValidator termValidator;
	
	public void setTermValidator(TermValidator termValidator) {
		this.termValidator = termValidator;
	}
	
	public boolean supports(Class clazz) {
		return FormStudentQuery.class.isAssignableFrom(clazz);
	}
	
	public void validate(Object target, Errors errors) {
		FormStudentQuery fsq = (FormStudentQuery) target;
		if (fsq.getUseUserId()) {
			if (fsq.getUserId() == null || fsq.getUserId() == "") {
				errors.rejectValue("userId", "student_query.null_user", "User ID must be entered when using this filter");
			}
		}
		if (fsq.getUseModified()) {
			boolean not_null = true;
			if (fsq.getLastModifiedStart() == null) {
				errors.rejectValue("lastModifiedStart", "date.missing_start_date", "Enter the start date");
				not_null = false;
			}
			if (fsq.getLastModifiedEnd() == null) {
				errors.rejectValue("lastModifiedEnd", "date.missing_end_date", "Enter the end date");
				not_null = false;
			}
			if (not_null) {
				if (fsq.isStartDateAfterEndDate()) {
					errors.rejectValue("lastModifiedStart", "date.invalid_combination", "First date should not be greater than the second date");
				}
			}
		}
		if (fsq.getUseCatalogTerm()) {
			try {
				errors.pushNestedPath("catalogTerm");
				termValidator.validate(fsq.getCatalogTerm(), errors);
			}
			finally {
				errors.popNestedPath();
			}
		}
		if (fsq.getUseGradTerm()) {
			try {
				errors.pushNestedPath("gradTerm");
				termValidator.validate(fsq.getGradTerm(), errors);
			}
			finally {
				errors.popNestedPath();
			}
		}
	}
}