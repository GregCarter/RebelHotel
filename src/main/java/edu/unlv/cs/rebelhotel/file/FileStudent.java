package edu.unlv.cs.rebelhotel.file;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import edu.unlv.cs.rebelhotel.domain.Major;
import edu.unlv.cs.rebelhotel.domain.Term;
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
	private Set<Major> majors = new HashSet<Major>();
	private Term admitTerm;
	private Term gradTerm;


	public Set<FileStudent> convert(Collection<List<Line>> student) {
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
				fileStudent.setGradTerm(line.getGradTerm());
			}
			fileStudents.add(fileStudent);
		}
		return fileStudents;
	}
}
