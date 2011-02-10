package edu.unlv.cs.rebelhotel.service;

import org.springframework.security.access.prepost.PreAuthorize;

// simple methodinvocation security test/experiment
public class ServiceTest {
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String get1() {
		return new String("This is a test message");
	}
}