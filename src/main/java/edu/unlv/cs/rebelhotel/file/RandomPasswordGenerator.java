package edu.unlv.cs.rebelhotel.file;

import java.util.Random;

public class RandomPasswordGenerator {
	public String generateRandomPassword(){
		Integer max_pass_length = 12;
		Integer nextint = 179;
		String charset = "12345ABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%&abcdefghijklmnopqrstuvwxyz67890";
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		
		Integer pos;
		for (int i = 0; i < max_pass_length; i++) {
			pos = random.nextInt(charset.length());
        	sb.append(charset.charAt(pos));
		}
		
		return sb.toString();
	}
}