package edu.unlv.cs.rebelhotel.email;

import edu.unlv.cs.rebelhotel.domain.UserAccount;

public interface UserEmailService {

	public void sendStudentConfirmation(final UserAccount userAccount, final String password);
	
	public void sendAdminComfirmation(final UserAccount userAccount, final String password);
		
	public void sendNewPassword(final UserAccount userAccount, final String password);
	
	public void sendWorkConfirmation(final UserAccount userAccount, final String password);

}
	
	
	