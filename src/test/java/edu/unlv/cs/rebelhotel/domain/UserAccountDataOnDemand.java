package edu.unlv.cs.rebelhotel.domain;

import org.springframework.roo.addon.dod.RooDataOnDemand;

@RooDataOnDemand(entity = UserAccount.class)
public class UserAccountDataOnDemand {
	public UserAccount getNewTransientUserAccount(int index) {
        edu.unlv.cs.rebelhotel.domain.UserAccount obj = new edu.unlv.cs.rebelhotel.domain.UserAccount();
        obj.setPassword("password_" + index);
        obj.setPasswordEncoder(null);
        obj.setUserId("userId" + index);
        obj.setUserGroup(null);
        obj.setEnabled(Boolean.TRUE);
        return obj;
    }
}
