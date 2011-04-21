package edu.unlv.cs.rebelhotel.email;


import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import edu.unlv.cs.rebelhotel.domain.UserAccount;

public class SendNewPasswordPreparator implements MimeMessagePreparator {
	private UserAccount userAccount;
	private String template;
	private VelocityEngine templateEngine;

	public void prepare(MimeMessage mimeMessage) throws Exception {
		MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
		message.setTo(userAccount.getEmail());
		message.setFrom("webmaster@RebelHotel.unlv.edu");
		// message.setText("Welcome to UNLV RebelHotel Application, Your password is "
		// + user.getPassword());
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("userAccount", userAccount);
		String text = VelocityEngineUtils.mergeTemplateIntoString(
				templateEngine,
				template,
				model);
		message.setText(text, true);

	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public void setTemplateEngine(VelocityEngine templateEngine) {
		this.templateEngine = templateEngine;
	}
}