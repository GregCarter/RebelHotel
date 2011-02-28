package edu.unlv.cs.rebelhotel.domain.enums;

import org.springframework.context.i18n.LocaleContextHolder;

import edu.unlv.cs.rebelhotel.service.SpringApplicationContext;


public enum PayStatus {

    PAID, UNPAID, VOLUNTEER;
    
    public String toString() {
    	if (this != null) {
    		return SpringApplicationContext.getApplicationContext().getMessage("label_"+this.getClass().getName().toLowerCase().replaceAll("\\.", "_")+"_"+this.name().toLowerCase(), null, LocaleContextHolder.getLocale());
    	}
    	else {
    		return "";
    	}
    }
}
