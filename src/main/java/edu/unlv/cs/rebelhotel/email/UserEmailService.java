package edu.unlv.cs.rebelhotel.email;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;


import edu.unlv.cs.rebelhotel.domain.UserAccount;

public class UserEmailService {
	
    private JavaMailSender mailSender;
    
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;

    }

    public void PrepareMsg(){
    	MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator(){

	public void prepare(MimeMessage mimeMessage) throws Exception {
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		helper.setFrom("RebelHotel@unlv.edu");
		helper.setTo("");
				
				//helper.
				// TODO Auto-generated method stub
				
				}
			};
		this.mailSender.send(mimeMessagePreparator );
    	
    }
    
}

 