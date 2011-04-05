package edu.unlv.cs.rebelhotel.file;

import java.util.Random;

public class RandomPasswordGenerator {
	private static final int MAX_PASSWORD_LENGTH = 8;
	public String generateRandomPassword(){
		String charset = "12345ABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%&abcdefghijklmnopqrstuvwxyz67890";
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		
		Integer pos;
		for (int i = 0; i < MAX_PASSWORD_LENGTH; i++) {
			pos = random.nextInt(charset.length());
        	sb.append(charset.charAt(pos));
		}
		
		return sb.toString();
	}
}