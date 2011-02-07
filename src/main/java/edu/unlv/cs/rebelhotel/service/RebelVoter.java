package edu.unlv.cs.rebelhotel.service;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

// simple voter test/experiment

public class RebelVoter implements AccessDecisionVoter {
	public boolean supports(java.lang.Class<?> clazz) {
		return true;
	}
	
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}
	
	public int vote(Authentication authentication, java.lang.Object object, java.util.Collection<ConfigAttribute> attributes) {
		return ACCESS_GRANTED;
	}
}