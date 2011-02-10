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
				StringBuilder sb = new StringBuilder();
				sb.append(param.getSemester().toString()+" "+param.getTermYear().toString());
				return sb.toString();
			}
		};
	}
	
	Converter<edu.unlv.cs.rebelhotel.domain.Student, String> getStudentConverter() {
		return new Converter<edu.unlv.cs.rebelhotel.domain.Student, String>() {
			public String convert(edu.unlv.cs.rebelhotel.domain.Student param) {
				StringBuilder sb = new StringBuilder();
				sb.append("("+param.getNSHE().toString()+") "+param.getLastName()+" "+param.getFirstName());
				return sb.toString();
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
	}
	
}
