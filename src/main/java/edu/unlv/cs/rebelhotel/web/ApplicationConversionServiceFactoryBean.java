package edu.unlv.cs.rebelhotel.web;

import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.RooConversionService;
import org.springframework.validation.MessageCodesResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.convert.converter.Converter;

import edu.unlv.cs.rebelhotel.service.SpringApplicationContext;

import java.util.Iterator;
import java.util.Set;


/**
 * A central place to register application Converters and Formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {
	
	@Autowired
	private MessageSource messageSource;
	
	
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
	
	Converter<edu.unlv.cs.rebelhotel.domain.Major, String> getMajorConverter() {
		return new Converter<edu.unlv.cs.rebelhotel.domain.Major, String>() {
			public String convert(edu.unlv.cs.rebelhotel.domain.Major param) {
				return param.toString();
			}
		};
	}
	
	Converter<edu.unlv.cs.rebelhotel.domain.enums.PayStatus, String> getPayStatusConverter() {
		return new Converter<edu.unlv.cs.rebelhotel.domain.enums.PayStatus, String>() {
			public String convert(edu.unlv.cs.rebelhotel.domain.enums.PayStatus param) {
				return messageSource.getMessage("label_"+param.getClass().getName().toLowerCase().replaceAll("\\.", "_")+"_"+param.name().toLowerCase(), null, LocaleContextHolder.getLocale());
			}
		};
	}
	
	Converter<edu.unlv.cs.rebelhotel.domain.enums.Verification, String> getVerificationConverter() {
		return new Converter<edu.unlv.cs.rebelhotel.domain.enums.Verification, String>() {
			public String convert(edu.unlv.cs.rebelhotel.domain.enums.Verification param) {
				return messageSource.getMessage("label_"+param.getClass().getName().toLowerCase().replaceAll("\\.", "_")+"_"+param.name().toLowerCase(), null, LocaleContextHolder.getLocale());
			}
		};
	}
	
	Converter<edu.unlv.cs.rebelhotel.domain.enums.Semester, String> getSemesterConverter() {
		return new Converter<edu.unlv.cs.rebelhotel.domain.enums.Semester, String>() {
			public String convert(edu.unlv.cs.rebelhotel.domain.enums.Semester param) {
				return messageSource.getMessage("label_"+param.getClass().getName().toLowerCase().replaceAll("\\.", "_")+"_"+param.name().toLowerCase(), null, LocaleContextHolder.getLocale());
			}
		};
	}
	
	Converter<edu.unlv.cs.rebelhotel.domain.enums.Validation, String> getValidationConverter() {
		return new Converter<edu.unlv.cs.rebelhotel.domain.enums.Validation, String>() {
			public String convert(edu.unlv.cs.rebelhotel.domain.enums.Validation param) {
				return messageSource.getMessage("label_"+param.getClass().getName().toLowerCase().replaceAll("\\.", "_")+"_"+param.name().toLowerCase(), null, LocaleContextHolder.getLocale());
			}
		};
	}
	
	Converter<edu.unlv.cs.rebelhotel.domain.enums.VerificationType, String> getVerificationTypeConverter() {
		return new Converter<edu.unlv.cs.rebelhotel.domain.enums.VerificationType, String>() {
			public String convert(edu.unlv.cs.rebelhotel.domain.enums.VerificationType param) {
				return messageSource.getMessage("label_"+param.getClass().getName().toLowerCase().replaceAll("\\.", "_")+"_"+param.name().toLowerCase(), null, LocaleContextHolder.getLocale());
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
		registry.addConverter(getMajorConverter());
		
		registry.addConverter(getVerificationTypeConverter());
		registry.addConverter(getValidationConverter());
		registry.addConverter(getSemesterConverter());
		registry.addConverter(getPayStatusConverter());
		registry.addConverter(getVerificationConverter());
		
	}
	
}
