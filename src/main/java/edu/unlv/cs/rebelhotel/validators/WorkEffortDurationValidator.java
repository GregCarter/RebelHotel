package edu.unlv.cs.rebelhotel.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.unlv.cs.rebelhotel.domain.WorkEffortDuration;

@Component
public class WorkEffortDurationValidator implements Validator {
	public boolean supports(Class clazz) {
		return WorkEffortDuration.class.isAssignableFrom(clazz);
	}
	
	public void validate(Object target, Errors errors) {
		WorkEffortDuration wed = (WorkEffortDuration) target;
		if (wed.isStartDateAfterEndDate()) {
			// The first parameter is the "field" value used in the .jspx files
			// the second parameter is the localization value given in one of the .properties files (date.invalid_combination is defined in custom.properties)
			// The third parameter is a default value to use when the localization value is unavailable
			errors.rejectValue("startDate", "date.invalid_combination", "The first date must not be greater than the second date");
		}
	}
}