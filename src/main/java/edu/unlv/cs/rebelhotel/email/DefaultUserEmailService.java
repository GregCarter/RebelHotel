package edu.unlv.cs.rebelhotel.email;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.mail.internet.MimeMessage;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;
import org.springframework.ui.velocity.VelocityEngineUtils;

import edu.unlv.cs.rebelhotel.domain.Student;
import edu.unlv.cs.rebelhotel.domain.UserAccount;
import edu.unlv.cs.rebelhotel.domain.WorkEffort;

@Service
public class DefaultUserEmailService implements UserEmailService{

	private static final Logger LOG = LoggerFactory.getLogger(DefaultUserEmailService.class);
	private static final int MAX_BATCH = 500;
	private JavaMailSender mailSender;
	private Queue<MimeMessagePreparator> mailQueue = new ConcurrentLinkedQueue<MimeMessagePreparator>();


	public JavaMailSender getMailSender() {
		return mailSender;
	}

	// remove later; just for testing
	@Autowired
	private VelocityEngineFactoryBean engine;


	@Autowired
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	private void send(MimeMessagePreparator preparator){
		
		try{
			this.mailSender.send(preparator);
		} 
        catch (MailAuthenticationException ex) {
            LOG.warn(" Mail Server Failed Authorization", ex);            
        }
        catch(MailParseException ex){
        	LOG.warn(" Unable To Parse Email Message", ex);
        }
        catch(MailPreparationException ex){
        	LOG.error(" Mail Preparation Failure ", ex);
        }
        catch(MailSendException ex){
        	LOG.warn(" Error Sending Mail Send Exception ", ex);
        }
	}
	
	private void enqueue(MimeMessagePreparator preparator){
		mailQueue.add(preparator);
		
    }
	@Scheduled(fixedDelay=60000)
	public void processBatch(){
		for(int i =0; i< MAX_BATCH && !mailQueue.isEmpty(); i++){
			send(mailQueue.remove());
		}
			
	}
	
      
	public void sendStudentConfirmation(final UserAccount userAccount, final String password)  {

		enqueue(new MimeMessagePreparator(){

			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(userAccount.getEmail());
				message.setSubject("HOTEL WORK EXPERIENCE TRACKING SYSTEM ");
				message.setFrom("webmaster@RebelHotel.unlv.edu");
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("userAccount", userAccount);
				model.put("password", password);
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						engine.createVelocityEngine(),
						"/edu/unlv/cs/rebelhotel/email/student-confirmation.vm",
						model);
				message.setText(text, true);
	
				}
			});
			
	}
	
	
	
	public void sendAdminComfirmation(final UserAccount userAccount, final String password)  {
		
		enqueue(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(userAccount.getEmail());
				message.setSubject("HOTEL WORK EXPERIENCE TRACKING SYSTEM ");
				message.setFrom("webmaster@RebelHotel.unlv.edu");
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("userAccount", userAccount);
				model.put("password", password);
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						engine.createVelocityEngine(),
						"/edu/unlv/cs/rebelhotel/email/admin-confirmation.vm",
						model);
				message.setText(text, true);

			}
		});
		
}
	
	
	
	public void sendNewPassword(final UserAccount userAccount, final String password) {
		
		enqueue(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(userAccount.getEmail());
				message.setFrom("webmaster@RebelHotel.unlv.edu");
				message.setSubject("New Password from Hotel College");
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("userAccount", userAccount);
				model.put("password", password);
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						engine.createVelocityEngine(),
						"/edu/unlv/cs/rebelhotel/email/new-password.vm",
						model);
				message.setText(text, true);

			}
		});
		
}
	
	
	public void sendWorkConfirmation(final Student student, final WorkEffort workEffort){
		
		enqueue(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(student.getUserAccount().getEmail());
				message.setFrom("webmaster@RebelHotel.unlv.edu");
				message.setSubject("Work Confirmation from Hotel College");
				Map<String,Object> model = new HashMap<String,Object>();
				model.put("student", student);
				model.put("workEffort", workEffort);
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						engine.createVelocityEngine(),
						"/edu/unlv/cs/rebelhotel/email/work-confirmation.vm",
						model);
				message.setText(text, true);
			}
		});
		
	}	
}
