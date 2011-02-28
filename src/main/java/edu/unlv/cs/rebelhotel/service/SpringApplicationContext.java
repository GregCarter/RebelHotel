package edu.unlv.cs.rebelhotel.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

// implemented to easily test methodinvocation security ... 
public class SpringApplicationContext implements ApplicationContextAware {
	private static ApplicationContext context;
	
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		SpringApplicationContext.context = context;
	}
	
	public static ApplicationContext getApplicationContext() {
		return context;
	}
	
	public static Object getBean(String name) {
		return context.getBean(name);
	}
}