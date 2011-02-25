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
	
	public Set<FileStudent> convert(Hashtable<String, Set<Line>> table) {
		// go through each key, and convert the lines into
		// the student. so create a new fileStudent for each key->value
		
		//I would have used a case/switch, but it does not work for
		// string switch expressions.
		
		Set<FileStudent> fileStudents = new HashSet<FileStudent>();
		for (Enumeration<Set<Line>> student = table.elements(); student.hasMoreElements();){
			Set<Line> lines = student.nextElement();
			for (Line line : lines){
				FileStudent fileStudent = new FileStudent();
				fileStudent.setStudentId(line.getStudentId());
				fileStudent.setFirstName(line.getFirstName());
				fileStudent.setMiddleName(line.getMiddleName());
				fileStudent.setEmail(line.getEmail());
				
				Integer temp = Integer.parseInt(line.getAdmitTermYear());
				if (line.getAdmitTermSemester().equals("FALL")) {
					fileStudent.setAdmitTerm(new Term(temp, Semester.FALL));
				}
				else if (line.getAdmitTermSemester().equals("SPRING")){
					fileStudent.setAdmitTerm(new Term(temp, Semester.SPRING));
				}
				else if (line.getAdmitTermSemester().equals("SUMMER")){
					fileStudent.setAdmitTerm(new Term(temp, Semester.SUMMER));
				}
				temp = Integer.parseInt(line.getGradTermYear());
				if (line.getGradTermSemester().equals("FALL")) {
					fileStudent.setGradTerm(new Term(temp, Semester.FALL));
				}
				else if (line.getGradTermSemester().equals("SPRING")){
					fileStudent.setGradTerm(new Term(temp, Semester.SPRING));
				}
				else if (line.getGradTermSemester().equals("SUMMER")){
					fileStudent.setGradTerm(new Term(temp, Semester.SUMMER));
				}
			}
		}
		return fileStudents;
	}
}
