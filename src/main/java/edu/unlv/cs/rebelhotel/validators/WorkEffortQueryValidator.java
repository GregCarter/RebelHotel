package edu.unlv.cs.rebelhotel.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.unlv.cs.rebelhotel.form.FormStudentQuery;
import edu.unlv.cs.rebelhotel.form.FormWorkEffortQuery;

@Component
public class WorkEffortQueryValidator {
	public boolean supports(Class clazz) {
		return FormWorkEffortQuery.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {

		FormWorkEffortQuery fweq = (FormWorkEffortQuery) target;

		if (fweq.getStartDate() != null && fweq.getEndDate() != null) {
			if (fweq.getEndDate().before(fweq.getStartDate())) {

				errors.rejectValue("startDate", "date.invalid_combination",
						"The first date must not be greater than the second date");

			}
		}

		if (fweq.getUserId().length() < 10) {

		}

	}

}
