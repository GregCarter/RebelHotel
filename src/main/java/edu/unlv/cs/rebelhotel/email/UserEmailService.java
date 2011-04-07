package edu.unlv.cs.rebelhotel.email;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import edu.unlv.cs.rebelhotel.domain.UserAccount;

public class UserEmailService {
	
	    private JavaMailSender mailSender;
	    
	    public void setMailSender(JavaMailSender mailSender) {
	        this.mailSender = mailSender;
	
	    }
	
	    public void sendComfirmation(final UserAccount user){
	    	//do business calculations
	    	
	    	MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator(){
	
		public void prepare(MimeMessage mimeMessage) throws Exception {
			MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
			message.setTo(user.getEmail());
			message.setFrom("webmaster@RebelHotel.unlv.edu");
			message.setText("Welcome to UNLV RebelHotel Application, Your password is " + user.getPassword());
		}
	};
		this.mailSender.send(mimeMessagePreparator );
	    }
}


 