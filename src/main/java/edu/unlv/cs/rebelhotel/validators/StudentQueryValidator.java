package edu.unlv.cs.rebelhotel.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.unlv.cs.rebelhotel.form.FormStudentQuery;

@Component
public class StudentQueryValidator implements Validator {
	public boolean supports(Class clazz) {
		return FormStudentQuery.class.isAssignableFrom(clazz);
	}
	
	public void validate(Object target, Errors errors) {
		FormStudentQuery fsq = (FormStudentQuery) target;
		if (fsq.getUseUserId() == true) {
			if (fsq.getUserId() == null || fsq.getUserId() == "") {
				errors.rejectValue("userId", "student_query.null_user", "User ID must be entered when using this filter");
			}
		}
	}
}