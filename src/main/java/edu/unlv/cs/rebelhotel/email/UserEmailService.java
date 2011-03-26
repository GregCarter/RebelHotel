package edu.unlv.cs.rebelhotel.email;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import edu.unlv.cs.rebelhotel.domain.UserAccount;

public class UserEmailService {
	
    private MailSender mailSender;
    private SimpleMailMessage templateMessage;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }

    public void sendConfirmation(UserAccount user)
    {
        
            // Do the business calculations...

            // Call the collaborators to persist the order...

            // Create a thread safe "copy" of the template message and customize it
            SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
            msg.setTo(user.getEmail());
            msg.setText(
            		"SampleString"
            		/*
                "Dear " + .getCustomer().getFirstName()
                    + order.getCustomer().getLastName()
                    + ", thank you for placing order. Your order number is "
                    + order.getOrderNumber()
                    */
                    );
            try{
                this.mailSender.send(msg);
            }
            catch(MailException ex) {
                // simply log it and go on...
                System.err.println(ex.getMessage());            
            }
        }

    }

 