package edu.unlv.cs.rebelhotel.form;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import edu.unlv.cs.rebelhotel.domain.Major;
import edu.unlv.cs.rebelhotel.domain.Student;
import edu.unlv.cs.rebelhotel.domain.Term;

@RooJavaBean
@RooToString
public class FormStudentMajor {
	private Long id;
	
	private Integer version;

    private Set<Major> majors = new HashSet<Major>();
    
    public static FormStudentMajor createFromStudent(Student student) {
    	if (student == null) {
    		return null;
    	}
    	FormStudentMajor fsm = new FormStudentMajor();
    	
    	fsm.setId(student.getId());
    	fsm.setMajors(student.getMajors());
    	
    	return fsm;
    }
}
