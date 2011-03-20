package edu.unlv.cs.rebelhotel.file;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import edu.unlv.cs.rebelhotel.domain.Term;
import edu.unlv.cs.rebelhotel.file.Line;
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
	private Term requirementTerm;

	public Set<FileStudent> convert(Collection<List<Line>> student) {
		// go through each key, and convert the lines into
		// the student. so create a new fileStudent for each key->value

		//I would have used a case/switch, but it does not work for
		// string switch expressions.
		
		Set<FileStudent> fileStudents = new HashSet<FileStudent>();
		for (List<Line> lines : student) {
			FileStudent fileStudent = new FileStudent();
			for (Line line : lines){
				fileStudent.setStudentId(line.getStudentId());
				fileStudent.setFirstName(line.getFirstName());
				fileStudent.setMiddleName(line.getMiddleName());
				fileStudent.setLastName(line.getLastName());
				fileStudent.setEmail(line.getEmail());
				fileStudent.setMajors(line.getMajors());
				fileStudent.setAdmitTerm(line.getAdmitTerm());
				fileStudent.setRequirementTerm(line.getRequirementTerm());
				fileStudent.setGradTerm(line.getGradTerm());
			}
			fileStudents.add(fileStudent);
		}
		return fileStudents;
	}
}
