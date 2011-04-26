package edu.unlv.cs.rebelhotel.email;

import org.springframework.mail.javamail.JavaMailSender;

import edu.unlv.cs.rebelhotel.domain.Student;
import edu.unlv.cs.rebelhotel.domain.UserAccount;
import edu.unlv.cs.rebelhotel.domain.WorkEffort;

public interface UserEmailService {

	public void sendStudentConfirmation(final UserAccount userAccount, final String password);
	
	public void sendAdminComfirmation(final UserAccount userAccount, final String password);
		
	public void sendNewPassword(final UserAccount userAccount, final String password);
	
	public void sendWorkConfirmation(final Student student, final WorkEffort workEffort);

}
	
	
	