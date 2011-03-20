package edu.unlv.cs.rebelhotel.file;

import java.util.HashSet;
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
	private Set<String> majors = new HashSet<String>();
	private Term admitTerm;
	private Term requirementTerm;
	private Term gradTerm;

	public Line convert(List<String> tokens){
		Line line = new Line();
		String[] field = (String[]) tokens.toArray();
		line.setStudentId(field[0]);
		line.setLastName(field[1]);
		line.setFirstName(field[2]);
		line.setMiddleName(field[3]);
		line.setEmail(field[4]);

		Set<String> majors = line.getMajors();
		majors.add(field[5]);
		line.setMajors(majors);

		if (!field[6].equals(" ")) {
			majors.add(field[6]);
			line.setMajors(majors);
		}

		line.setAdmitTerm(doMakeTerm(field[7]));
		line.setRequirementTerm(doMakeTerm(field[8]));
		line.setGradTerm(doMakeTerm(field[9]));

		return line;
	}

	public Term makeTerm(String yearAndTerm) {
		char[] character = {0,0,0,0};
		Integer termYear = null;
		Semester semester = null;

		yearAndTerm.getChars(0,4,character,0);
		termYear = convertToYear(character[0], character[1], character[2]);
		semester = convertToSemester(character[3]);
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
		
		/*if (Term.doesExist(term.getSemester(), term.getTermYear())) {
			term.merge();
		} else {
			term.persist();
		}*/
		
		term.merge();
		
		return term;
	}

	private Integer convertToYear(char century, char leftYear, char rightYear) {
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

	private Semester convertToSemester(char semester){
		if ('8' == semester) {
			return Semester.FALL;
		} else if ('2' == semester) {
			return Semester.SPRING;
		} else if ('5' == semester) {
			return Semester.SUMMER;
		} else {
			throw new IllegalArgumentException("Invalid semester:" + semester);
		}
	} 

	private Term doMakeTerm(String term) {
		if (term.equals(" ")){
			return null;
		} else {
			return makeTerm(term);
		}
	}
}