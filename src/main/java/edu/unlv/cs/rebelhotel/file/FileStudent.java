package edu.unlv.cs.rebelhotel.file;

import java.util.Enumeration;
import java.util.Set;
import java.util.HashSet;

import edu.unlv.cs.rebelhotel.domain.Term;
import edu.unlv.cs.rebelhotel.domain.enums.Semester;
import edu.unlv.cs.rebelhotel.file.Line;
import java.util.Hashtable;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
public class FileStudent {
	private String studentId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String email;
	private Set<String> major = new HashSet<String>();
	private Term admitTerm;
	private Term gradTerm;
	
	public FileStudent() {
	}
	
	public Set<FileStudent> convert(Hashtable<String, Set<Line>> entries) {
		// go through each key, and convert the lines into
		// the student. so create a new fileStudent for each key->value
		
		/*		// I would have used a case/switch, but it does not work for
		// string switch expressions.
		temp = Integer.parseInt(fileStudent.getAdmitTermYear());
		if (fileStudent.getAdmitTermSemester().equals("FALL")) {
			student.setAdmitTerm(new Term(temp, Semester.FALL));
		}
		else if (fileStudent.getAdmitTermSemester().equals("SPRING")){
			student.setAdmitTerm(new Term(temp, Semester.SPRING));
		}
		else if (fileStudent.getAdmitTermSemester().equals("SUMMER")){
			student.setAdmitTerm(new Term(temp, Semester.SUMMER));
		}
		
		temp = Integer.parseInt(fileStudent.getGradTermYear());
		if (fileStudent.getGradTermSemester().equals("FALL")) {
			student.setGradTerm(new Term(temp, Semester.FALL));
		}
		else if (fileStudent.getGradTermSemester().equals("SPRING")){
			student.setGradTerm(new Term(temp, Semester.SPRING));
		}
		else if (fileStudent.getGradTermSemester().equals("SUMMER")){
			student.setGradTerm(new Term(temp, Semester.SUMMER));
		}*/
		Set<FileStudent> fileStudents = new HashSet<FileStudent>();
		for (Enumeration<Set<Line>> e = entries.elements(); e.hasMoreElements();){
			e.nextElement();
			for (Line each : ){
				
			}
				
		}
		
		return fileStudents;
	}
}
