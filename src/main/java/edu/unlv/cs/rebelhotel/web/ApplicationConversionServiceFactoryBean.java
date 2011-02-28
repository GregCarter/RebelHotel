package edu.unlv.cs.rebelhotel.web;

import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.RooConversionService;

import org.springframework.core.convert.converter.Converter;

import java.util.Iterator;
import java.util.Set;


/**
 * A central place to register application Converters and Formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	Converter<Set, String> getSetConverter() {
		return new Converter<Set, String>() {
			public String convert(Set param) {
				StringBuilder sb = new StringBuilder();
				Iterator it = param.iterator();
				while (it.hasNext()) {
					sb.append(it.next().toString()+" ");
				}
				return sb.toString();
			}
		};
	}
	
	Converter<edu.unlv.cs.rebelhotel.domain.Term, String> getTermConverter() {
		return new Converter<edu.unlv.cs.rebelhotel.domain.Term, String>() {
			public String convert(edu.unlv.cs.rebelhotel.domain.Term param) {
				return param.toString();
			}
		};
	}
	
	Converter<edu.unlv.cs.rebelhotel.domain.Student, String> getStudentConverter() {
		return new Converter<edu.unlv.cs.rebelhotel.domain.Student, String>() {
			public String convert(edu.unlv.cs.rebelhotel.domain.Student param) {
				return param.toString();
			}
		};
	}
	
	Converter<edu.unlv.cs.rebelhotel.domain.Supervisor, String> getSupervisorConverter() {
		return new Converter<edu.unlv.cs.rebelhotel.domain.Supervisor, String>() {
			public String convert(edu.unlv.cs.rebelhotel.domain.Supervisor param) {
				return param.toString();
			}
		};
	}
	
	Converter<edu.unlv.cs.rebelhotel.domain.Employer, String> getEmployerConverter() {
		return new Converter<edu.unlv.cs.rebelhotel.domain.Employer, String>() {
			public String convert(edu.unlv.cs.rebelhotel.domain.Employer param) {
				return param.toString();
			}
		};
	}
	
	Converter<edu.unlv.cs.rebelhotel.domain.WorkEffortDuration, String> getWorkEffortDurationConverter() {
		return new Converter<edu.unlv.cs.rebelhotel.domain.WorkEffortDuration, String>() {
			public String convert(edu.unlv.cs.rebelhotel.domain.WorkEffortDuration param) {
				return param.toString();
			}
		};
	}
	
	Converter<edu.unlv.cs.rebelhotel.domain.UserAccount, String> getUserAccountConverter() {
		return new Converter<edu.unlv.cs.rebelhotel.domain.UserAccount, String>() {
			public String convert(edu.unlv.cs.rebelhotel.domain.UserAccount param) {
				return param.toString();
			}
		};
	}
	
	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
		
		registry.addConverter(getSetConverter());
		registry.addConverter(getTermConverter());
		registry.addConverter(getStudentConverter());
		registry.addConverter(getSupervisorConverter());
		registry.addConverter(getEmployerConverter());
		registry.addConverter(getWorkEffortDurationConverter());
		registry.addConverter(getUserAccountConverter());
		
	}
	
}
