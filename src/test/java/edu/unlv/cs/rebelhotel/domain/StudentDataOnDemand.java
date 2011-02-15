package edu.unlv.cs.rebelhotel.domain;

import org.springframework.roo.addon.dod.RooDataOnDemand;

import edu.unlv.cs.rebelhotel.domain.Student;

@RooDataOnDemand(entity = Student.class)
public class StudentDataOnDemand {
	public Student getNewTransientStudent(int index) {
        edu.unlv.cs.rebelhotel.domain.Student obj = new edu.unlv.cs.rebelhotel.domain.Student();
        obj.setNSHE(new Long(1000000000).longValue() + index);
        obj.setFirstName("firstName_" + index);
        obj.setMiddleName("middleName_" + index);
        obj.setLastName("lastName_" + index);
        obj.setMajor1("major1_" + index);
        obj.setMajor2("major2_" + index);
        obj.setEmail("email_" + index);
        obj.setAdmitTerm(null);
        obj.setGradTerm(null);
        return obj;
    }
}
