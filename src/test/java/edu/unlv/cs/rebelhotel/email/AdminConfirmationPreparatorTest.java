package edu.unlv.cs.rebelhotel.email;

import static org.easymock.EasyMock.*;

import java.io.Writer;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.unlv.cs.rebelhotel.domain.UserAccount;

/*
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
public class AdminConfirmationPreparatorTest {
	
	@Autowired
	private AdminConfirmationPreparator instance;

	@Test
	public void testPrepare() throws Exception {
		MimeMessage mimeMessage = createMock(MimeMessage.class);
		UserAccount ua = new UserAccount();
		ua.setEmail("asdf");
		mimeMessage.setRecipient(eq(RecipientType.TO), (InternetAddress)anyObject());
		expectLastCall();
		mimeMessage.setFrom( (InternetAddress)anyObject());
		expectLastCall();
		
		mimeMessage.setContent("","text/html");
		expectLastCall();

		replay(mimeMessage);
		
		instance.setUserAccount(ua);
		instance.prepare(mimeMessage);
	}

}
*/