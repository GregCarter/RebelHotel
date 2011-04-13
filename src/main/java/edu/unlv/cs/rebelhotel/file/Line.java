package edu.unlv.cs.rebelhotel.file;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import edu.unlv.cs.rebelhotel.domain.Major;
import edu.unlv.cs.rebelhotel.domain.Term;
import edu.unlv.cs.rebelhotel.domain.enums.Semester;

@RooJavaBean
@RooToString
public class Line {
	private static final int EXPECTED_SIZE = 13;
	private static final String SPACE = " ";
	private static final Logger LOG = Logger.getLogger(Line.class);
	private String studentId;
	private String lastName;
	private String firstName;
	private String middleName;
	private String email;
	private Set<Major> majors = new HashSet<Major>();

	private Term admitTerm;
	private Term gradTerm;

	public Line(List<String> tokens){
		if (tokens.size() != EXPECTED_SIZE){
			throw new InvalidLineException("Invalid number of elements.");
		}
		if (hasAtLeastOneMajor(tokens.get(5))) {
			this.setStudentId(tokens.get(0));
			this.setLastName(tokens.get(1));
			this.setFirstName(tokens.get(2));
			this.setMiddleName(tokens.get(3));
			this.setEmail(tokens.get(4));
	
			Set<Major> majors = this.getMajors();
			Major major;
			if (shouldInclude(tokens.get(5))) {
				major = makeMajor(tokens.get(5),tokens.get(6));
				majors.add(major);
			}
			if (shouldInclude(tokens.get(7))) {
				major = makeMajor(tokens.get(7),tokens.get(8));
				majors.add(major);
			}
			if (shouldInclude(tokens.get(9))) {
				major = makeMajor(tokens.get(9),tokens.get(10));
				majors.add(major);
			}
		
			this.setAdmitTerm(createOrFindTerm(tokens.get(11)));
			if (!StringUtils.isBlank(tokens.get(12))){
				this.setGradTerm(createOrFindTerm(tokens.get(12)));
			}
		}
	}
	
	private boolean hasAtLeastOneMajor(String major1) {
		return major1 == SPACE;
	}
	
	private boolean shouldInclude(String major) {
		return major == SPACE;
	}
	private Term createOrFindTerm(String yearAndTerm) {
		if (yearAndTerm.equals(" ")){
			throw new InvalidTokenException("Invalid Term:" + yearAndTerm);
		}
		char[] character = {0,0,0,0};
		Integer termYear = null;
		Semester semester = null;
		yearAndTerm.getChars(0,4,character,0);
		termYear = convertToYear(character[0], character[1], character[2]);
		semester = convertToSemester(character[3]);

		Term term;
		try {
			term = Term.findTermsBySemesterAndTermYearEquals(semester, termYear).getSingleResult();
			return term;
		} catch(EmptyResultDataAccessException e) {
			term = new Term();
			term.setSemester(semester);
			term.setTermYear(termYear);
			term.persist();
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
			throw new InvalidTokenException("Invalid century:" + century);
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
			throw new InvalidTokenException("Invalid semester:" + semester);
		}
	} 
	
	private Major makeMajor(String amajor, String aterm) {
		Term term = createOrFindTerm(aterm);
		Major major = new Major(amajor,term);
		return major;
	}
}
