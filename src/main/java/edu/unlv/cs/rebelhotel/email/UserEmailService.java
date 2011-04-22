package edu.unlv.cs.rebelhotel.email;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;
import org.springframework.ui.velocity.VelocityEngineUtils;

import edu.unlv.cs.rebelhotel.domain.UserAccount;

@Service
public class UserEmailService {

	private JavaMailSender mailSender;
	/*
	@Autowired
	private AdminConfirmationPreparator adminConfirmation;
	private StudentConfirmationPreparator studentConfirmation;
	private SendNewPasswordPreparator sendNewPassword;
	private SendWorkEffortPreparator sendWorkEffotConfirmation;
	*/
	
	// remove later; just for testing
	@Autowired
	private VelocityEngineFactoryBean engine;


	@Autowired
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendStudentConfirmation(final UserAccount userAccount, final String password) throws Exception {

		MimeMessagePreparator preparator = new MimeMessagePreparator(){

			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(userAccount.getEmail());
				message.setFrom("webmaster@RebelHotel.unlv.edu");
				Map model = new HashMap();
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
	/*
	@Autowired
	public void setStudentConfirmation(StudentConfirmationPreparator studentConfirmation){
		this.studentConfirmation = studentConfirmation;
	
	} */
	//=============================================================
	
	public void sendAdminComfirmation(final UserAccount userAccount, final String password) throws Exception {
		
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(userAccount.getEmail());
				message.setFrom("webmaster@RebelHotel.unlv.edu");
				Map model = new HashMap();
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
	public void sendNewPassword(final UserAccount userAccount, final String password) throws Exception {
		
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(userAccount.getEmail());
				message.setFrom("webmaster@RebelHotel.unlv.edu");
				Map model = new HashMap();
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
	
	public void sendWorkConfirmation(final UserAccount userAccount, final String password) throws Exception {
		
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(userAccount.getEmail());
				message.setFrom("webmaster@RebelHotel.unlv.edu");
				Map model = new HashMap();
				model.put("userAccount", userAccount);
				model.put("password", password);
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

	
	
	
	
	/*
	public AdminConfirmationPreparator getAdminConfirmation() {
		return this.adminConfirmation;
	}
	
	@Autowired
	public void setAdminConfirmation(AdminConfirmationPreparator adminConfirmation){
		this.adminConfirmation = adminConfirmation;
	}
	
	//=============================================================
	public void sendNewPassword (final UserAccount userAccount)
	{
		SendNewPasswordPreparator sendNewPassword = getSendNewPassword();
		sendNewPassword.setUserAccount(userAccount);
		this.mailSender.send(sendNewPassword);
	}
	
	public SendNewPasswordPreparator getSendNewPassword() {
		return this.sendNewPassword;
	}
	
	@Autowired 
	public void setSendNewPassword(SendNewPasswordPreparator sendNewPassword){
	this.sendNewPassword = sendNewPassword;
	}
	//=============================================================
	
	public void sendWorkEffortConfirmation(final UserAccount userAccount)
	{
		SendWorkEffortPreparator sendWorkEffortConfirmation = getSendWorkEffortConfirmation();
		sendWorkEffortConfirmation.setUserAccount(userAccount);
		this.mailSender.send(sendWorkEffortConfirmation);
	}
	
	public SendWorkEffortPreparator getSendWorkEffortConfirmation(){
		return this.sendWorkEffotConfirmation;
	}
	
	@Autowired 
	public void setSendWorkEfforConfirmation( SendWorkEffortPreparator sendWorkEffortConfirmation){
		this.sendWorkEffotConfirmation = sendWorkEffortConfirmation;
	}

}
*/