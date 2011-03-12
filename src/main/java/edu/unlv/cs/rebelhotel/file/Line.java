package edu.unlv.cs.rebelhotel.file;

import java.util.List;
import java.util.Set;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import edu.unlv.cs.rebelhotel.domain.Term;
import edu.unlv.cs.rebelhotel.domain.enums.Semester;

@RooJavaBean
@RooToString
public class Line {
	private String studentId;
	private String lastName;
	private String firstName;
	private String middleName;
	private String email;
	private Set<String> majors;
	private Term admitTerm;
	private Term requirementTerm;
	private Term gradTerm;

	public Line convert(List<String> tokens) {
		Line line = new Line();
		String[] field = (String[]) tokens.toArray();
		line.setStudentId(field[0]);
		line.setLastName(field[1]);
		line.setFirstName(field[2]);
		line.setMiddleName(field[3]);
		line.setEmail(field[4]);
		line.getMajors().add(field[5]);
		
		if (!field[6].equals("")) {
			line.getMajors().add(field[6]);
		}
		
		line.setAdmitTerm(makeTerm(field[7]));
		line.setRequirementTerm(makeTerm(field[8]));
		line.setGradTerm(makeTerm(field[9]));
		return line;
	}
	
	public Term makeTerm(String yearAndTerm) {
		char[] character = {0,0,0,0};
		Integer termYear = null;
		String semester = null;
		
		yearAndTerm.getChars(0,4,character,0);
		termYear = convertToYear(character[0], character[1], character[2]);
		semester = convertToSemester(character[4]);
		Term term = new Term();
		term.setTermYear(termYear);
		
		if (semester.equals(Semester.FALL)) {
			term.setSemester(Semester.FALL);
		} else if (semester.equals(Semester.SPRING)) {
			term.setSemester(Semester.SPRING);
		} else if (semester.equals(Semester.SUMMER)) {
			term.setSemester(Semester.SUMMER);
		} else {
			throw new IllegalArgumentException("Invalid semester:" + semester);
		}
		return term;
	}
	
	public Integer convertToYear(char century, char leftYear, char rightYear) {
		Integer year = null;
		if ('0' == century) { 
			year = 1900;
		} else if ('2' == century) { 
			year = 2000;
		} else {
			throw new IllegalArgumentException("Invalid century:" + century);
		}
		String yearString = new StringBuilder().append(leftYear).append(rightYear).toString();
		year += Integer.valueOf(yearString);
		return year;
	}
	
	public String convertToSemester(char semester){
		if ('8' == semester) {
			return "FALL";
		} else if ('2' == semester) {
			return "SPRING";
		} else if ('5' == semester) {
			return "SUMMER";
		} else {
			throw new IllegalArgumentException("Invalid semester:" + semester);
		}
	}
}
