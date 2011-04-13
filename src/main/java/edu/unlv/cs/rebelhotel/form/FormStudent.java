package edu.unlv.cs.rebelhotel.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import edu.unlv.cs.rebelhotel.domain.Student;
import edu.unlv.cs.rebelhotel.domain.Term;

@RooJavaBean
@RooToString
public class FormStudent {
	private Long id;
	
	private Integer version;

    @NotNull
    private String userId;
    
    @NotNull
    private String email;

    @NotNull
    @Size(min = 2)
    private String firstName;

    private String middleName;

    private String lastName;

    private Term admitTerm;

    private Term gradTerm;

    private Boolean codeOfConductSigned;
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UserId: ").append(getUserId()).append(", ");
        sb.append("Email: ").append(getEmail()).append(", ");
        sb.append("FirstName: ").append(getFirstName()).append(", ");
        sb.append("MiddleName: ").append(getMiddleName()).append(", ");
        sb.append("LastName: ").append(getLastName()).append(", ");
        sb.append("AdmitTerm: ").append(getAdmitTerm()).append(", ");
        sb.append("GradTerm: ").append(getGradTerm()).append(", ");
        sb.append("CodeOfConductSigned: ").append(getCodeOfConductSigned());
        return sb.toString();
    }
    
    public static FormStudent createFromStudent(Student student) {
    	if (student == null) {
    		return null;
    	}
    	FormStudent fs = new FormStudent();
    	
    	fs.setId(student.getId());
    	fs.setUserId(student.getUserId());
    	fs.setEmail(student.getEmail());
    	fs.setFirstName(student.getFirstName());
    	fs.setMiddleName(student.getMiddleName());
    	fs.setLastName(student.getLastName());
    	fs.setAdmitTerm(student.getAdmitTerm());
    	fs.setGradTerm(student.getGradTerm());
    	fs.setCodeOfConductSigned(student.getCodeOfConductSigned());
    	
    	return fs;
    }
}
