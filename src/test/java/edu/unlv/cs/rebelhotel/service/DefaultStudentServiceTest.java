package edu.unlv.cs.rebelhotel.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.unlv.cs.rebelhotel.file.DefaultStudentService;

public class DefaultStudentServiceTest {
	DefaultStudentService instance;
	
	@Before
	public void setUp() throws Exception {
		instance = new DefaultStudentService();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFoo() {
		//String expected = "";
		//String actual = instance.foo();
		//assertEquals("I have not done anything yet so it should return null",expected,actual);
	}

}
