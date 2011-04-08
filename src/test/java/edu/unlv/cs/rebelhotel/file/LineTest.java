package edu.unlv.cs.rebelhotel.file;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.unlv.cs.rebelhotel.domain.Major;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
@Transactional
public class LineTest {
	private static final String VALID_DATA = "1000011622,Holmes,Katherine,Marilyn,HOLMESK4@UNLV.NEVADA.EDU,HOSBSHA,2048, , , , ,2048, ";
	
	@Test(expected=InvalidLineException.class)
	public void shouldFailIfGivenIncorrectSize() {
		List<String> tokens = new ArrayList<String>();
		Line instance = new Line(tokens);
	}
	
	@Test
	public void shouldHaveEmptyMajorSetIfMajorTokenIsInvalid() {
		String line = "1000011622,Holmes,Katherine,Marilyn,HOLMESK4@UNLV.NEVADA.EDU,PSYMIN,2048, , , , ,2048, ";
		List<String> tokens = Arrays.asList(line.split(","));
		Line instance = new Line(tokens);
		int actualMajorSize = instance.getMajors().size();
		int expectedMajorSize = 0;
		assertEquals("The major size should be empty if there are no valid majors.", expectedMajorSize,actualMajorSize);
	}
	
	private Line createValidLine() {
		List<String> tokens = Arrays.asList(VALID_DATA.split(","));
		return new Line(tokens);
	}
}
