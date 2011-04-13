package edu.unlv.cs.rebelhotel.email;


import javax.mail.internet.InternetAddress;
import org.apache.velocity.app.VelocityEngine;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import edu.unlv.cs.rebelhotel.domain.UserAccount;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;


@Service
public class UserEmailService {
	
	    private JavaMailSender mailSender;
	    private VelocityEngine velocityEngine;
	    
	    @Autowired
	    public void setVelocityEngine(VelocityEngine velocityEngine) {
			this.velocityEngine = velocityEngine;
		}

		@Autowired
	    public void setMailSender(JavaMailSender mailSender) {
	        this.mailSender = mailSender;
	    }
	    
	
		public void sendStudentConfirmation(final UserAccount user)
		{
			
		}
	    public void sendAdminComfirmation(final UserAccount user){
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
	    
	    public void sendNewPassword(final UserAccount user)
	    {
	    	
	    }
	    
	    public void sendWorkEffortConfirmation(final UserAccount user)
	    {
	    	
	    }
	    }



 