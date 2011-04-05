package edu.unlv.cs.rebelhotel.file;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RandomPasswordGeneratorTest {

	@Test
	public void shouldGenerateRandomPassword() {
		RandomPasswordGenerator instance = new RandomPasswordGenerator();
		String actualPassword = instance.generateRandomPassword();
		int actualLength = actualPassword.length();
		int expectedLength = 8;
		assertEquals("This should generate a random password of at least eight characters.",expectedLength,actualLength);
	}
}
