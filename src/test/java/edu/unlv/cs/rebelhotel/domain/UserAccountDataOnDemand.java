package edu.unlv.cs.rebelhotel.domain;

import java.util.List;

import org.springframework.roo.addon.dod.RooDataOnDemand;

@RooDataOnDemand(entity = UserAccount.class)
public class UserAccountDataOnDemand {
	private List<UserAccount> data;
	
	public UserAccount getNewTransientUserAccount(int index) {
        edu.unlv.cs.rebelhotel.domain.UserAccount obj = new edu.unlv.cs.rebelhotel.domain.UserAccount();
        obj.setPassword("password_" + index);
        obj.setPasswordEncoder(null);
        obj.setUserId("userId" + index);
        obj.setUserGroup(null);
        obj.setEnabled(Boolean.TRUE);
        return obj;
    }
	
	public UserAccount getSpecificUserAccount(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) {
        	index = data.size();
        	edu.unlv.cs.rebelhotel.domain.UserAccount obj = getNewTransientUserAccount(index);
            obj.persist();
            obj.flush();
            data.add(obj);
        }
        UserAccount obj = data.get(index);
        return UserAccount.findUserAccount(obj.getId());
    }
}
