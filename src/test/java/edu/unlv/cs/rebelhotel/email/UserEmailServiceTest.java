package edu.unlv.cs.rebelhotel.email;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;

import edu.unlv.cs.rebelhotel.domain.UserAccount;

public class UserEmailServiceTest {

	private UserEmailService instance;
	private JavaMailSender mailSender;
	private AdminConfirmationPreparator adminConfirmation;
/*
	@Before
	public void setUp(){
		instance = new UserEmailService();
		mailSender = createStrictMock(JavaMailSender.class);
		instance.setMailSender(mailSender);
		adminConfirmation = createStrictMock(AdminConfirmationPreparator.class);
		instance.setAdminConfirmation(adminConfirmation);
	}
	
	@Test
	public void testSendStudentConfirmation() {
		fail("Not yet implemented");
	}

	@Test
	public void testSendAdminComfirmation() throws Exception {
		UserAccount userAccount = new UserAccount();
		userAccount.setEmail("red@localhost");
		
		mailSender.send(adminConfirmation);
		expectLastCall().andThrow(new MailSendException("this is the message"));
		adminConfirmation.setUserAccount(userAccount);
		replay(mailSender);
		replay(adminConfirmation);
		
		String password= "";
		instance.sendAdminComfirmation(userAccount, password);
	}

	@Test
	public void testSendNewPassword() {
		fail("Not yet implemented");
	}

	@Test
	public void testSendWorkEffortConfirmation() {
		fail("Not yet implemented");
	}
*/
}
