package james;

import org.junit.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


public class EmailTest {

	@Test
	public void createEmail(){
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost("localhost");
		SimpleMailMessage simpleMessage = new SimpleMailMessage();
		simpleMessage.setTo("red@localhost");
		simpleMessage.setFrom("blue@localhost");
		simpleMessage.setText("this is the text");
		sender.send(simpleMessage);
	}
}
