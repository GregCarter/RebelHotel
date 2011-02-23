package edu.unlv.cs.rebelhotel.file;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
public class Line {
	private String studentId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String email;
	private String major;
	private String admitTermYear;
	private String admitTermSemester;
	private String gradTermYear;
	private String gradTermSemester;
	
	public Line(){}
	public Line(String studentId, 
			String firstName, 
			String middleName, 
			String lastName, 
			String email, 
			String major,
			String admitTermYear,
			String admitTermSemester,
			String gradTermYear,
			String gradTermSemester) {
		this.studentId = studentId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.email = email;
		this.major = major;
		this.admitTermYear = admitTermYear;
		this.admitTermSemester = admitTermSemester;
		this.gradTermYear = gradTermYear;
		this.gradTermSemester = gradTermSemester;
	}
}
