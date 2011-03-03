package edu.unlv.cs.rebelhotel.file;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
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
	private Set<String> majors = new HashSet<String>();
	private Term admitTerm;
	private Term gradTerm; // are we going to put gradTerm in Majors?
	
	public FileStudent() {
	}
	
	public Set<FileStudent> convert(Map<String, Set<Line>> entries) {
		// go through each key, and convert the lines into
		// the student. so create a new fileStudent for each key->value
		
		//I would have used a case/switch, but it does not work for
		// string switch expressions.
		
		Set<FileStudent> fileStudents = new HashSet<FileStudent>();
		Collection<Set<Line>> student = entries.values();
		for (student.hasMoreElements();){
			FileStudent fileStudent = new FileStudent();
			Set<Line> lines = student.nextElement();
	
			// I commented out the code here because the implementation will change
			// once we know the order in which the data will appear...
			for (Line line : lines){
				/*fileStudent.setStudentId(line.getStudentId());
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
				fileStudent.majors.add(line.getMajor());*/
			}
			fileStudents.add(fileStudent);
		}
		return fileStudents;
	}
}
