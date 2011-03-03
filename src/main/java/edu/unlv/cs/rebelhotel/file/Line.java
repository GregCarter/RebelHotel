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
	private String requirementTermYear;
	private String requirementTermSemester;
	
	public Line(){}
}
