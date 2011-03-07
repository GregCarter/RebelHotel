package edu.unlv.cs.rebelhotel.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.dod.RooDataOnDemand;

import edu.unlv.cs.rebelhotel.domain.Student;

@RooDataOnDemand(entity = Student.class)
public class StudentDataOnDemand {
	
	@Autowired
	UserAccountDataOnDemand uadod;
	
	public Student getNewTransientStudent(int index) {
        edu.unlv.cs.rebelhotel.domain.Student obj = new edu.unlv.cs.rebelhotel.domain.Student();
        obj.setUserId("userId_" + index);
        obj.setEmail("email_" + index);
        obj.setFirstName("firstName_" + index);
        obj.setMiddleName("middleName_" + index);
        obj.setLastName("lastName_" + index);
        //obj.setMajor1("major1_" + index);
        //obj.setMajor2("major2_" + index);
        obj.setAdmitTerm(null);
        obj.setGradTerm(null);
        obj.setUserAccount(uadod.getSpecificUserAccount(index));
        return obj;
    }
}
