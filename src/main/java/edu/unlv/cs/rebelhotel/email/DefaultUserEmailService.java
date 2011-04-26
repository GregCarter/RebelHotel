package edu.unlv.cs.rebelhotel.email;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;
import org.springframework.ui.velocity.VelocityEngineUtils;

import edu.unlv.cs.rebelhotel.domain.Student;
import edu.unlv.cs.rebelhotel.domain.UserAccount;
import edu.unlv.cs.rebelhotel.domain.WorkEffort;

@Service
public class DefaultUserEmailService implements UserEmailService{

	private JavaMailSender mailSender;


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

	public void sendStudentConfirmation(final UserAccount userAccount, final String password)  {

		MimeMessagePreparator preparator = new MimeMessagePreparator(){

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
			};
			this.mailSender.send(preparator);
	}

	
	
	@Async
	public void sendAdminComfirmation(final UserAccount userAccount, final String password)  {
		
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
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
		};
		this.mailSender.send(preparator);
	}
	
	
	@Async
	public void sendNewPassword(final UserAccount userAccount, final String password) {
		
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
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
		};
		this.mailSender.send(preparator);
	}
	@Async
	public void sendWorkConfirmation(final Student student, final WorkEffort workEffort){
		
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
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
		};
		this.mailSender.send(preparator);
	}
	
}
