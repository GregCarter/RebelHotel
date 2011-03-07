package edu.unlv.cs.rebelhotel.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.unlv.cs.rebelhotel.domain.Term;

@Component
public class TermValidator implements Validator {
	public boolean supports (Class clazz) {
		return Term.class.isAssignableFrom(clazz);
	}
	
	public void validate(Object target, Errors errors) {
		Term term = (Term) target;
		if (term.getTermYear() == null) {
			errors.rejectValue("termYear", "term.null_year", "Year is required");
		}
	}
}