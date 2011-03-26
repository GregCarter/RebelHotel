package edu.unlv.cs.rebelhotel.file;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.TypedQuery;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import edu.unlv.cs.rebelhotel.domain.Major;
import edu.unlv.cs.rebelhotel.domain.Term;
import edu.unlv.cs.rebelhotel.domain.enums.Departments;
import edu.unlv.cs.rebelhotel.domain.enums.Semester;

import edu.unlv.cs.rebelhotel.file.enums.FileDepartments;

@RooJavaBean
@RooToString
public class Line {
	private String studentId;
	private String lastName;
	private String firstName;
	private String middleName;
	private String email;
	private Set<Major> majors = new HashSet<Major>();
	private Term admitTerm;
	private Term gradTerm;

	public Line convert(List<String> tokens){
		System.out.println(tokens); 
		Line line = new Line();
		String[] field = (String[]) tokens.toArray();
		line.setStudentId(field[0]);
		line.setLastName(field[1]);
		line.setFirstName(field[2]);
		line.setMiddleName(field[3]);
		line.setEmail(field[4]);

		Set<Major> majors = line.getMajors();
		Major major;
		boolean shouldIgnore = shouldIgnore(field[5]);
		if (shouldIgnore) {
			return null;
		} else {
			major = makeMajor(field[5],field[6]);
			majors.add(major);
		}
		shouldIgnore = shouldIgnore(field[7]);
		if (!shouldIgnore) {
			major = makeMajor(field[7],field[8]);
			majors.add(major);
		}
		shouldIgnore = shouldIgnore(field[9]);
		if (!shouldIgnore) {
			major = makeMajor(field[9],field[10]);
			majors.add(major);
		}
	
		line.setAdmitTerm(doMakeTerm(field[11]));
		line.setGradTerm(doMakeTerm(field[12]));
	
		return line;
	}
	
	private boolean shouldIgnore(String major) {
		boolean ignore = (major.equals(FileDepartments.RECBS.toString()) 
				|| major.equals(FileDepartments.RECMIN.toString()) 
				|| major.equals(FileDepartments.RECPGMBS.toString())
				|| major.equals(FileDepartments.ENTMIN.toString())
				|| major.equals(" "));
		if (ignore) {
			return true;
		} else {
			return false;
		}
	}

	private Term makeTerm(String yearAndTerm) {
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
			Term aterm = makeTerm(term);
			TypedQuery<Term> q = Term.findTermsBySemesterAndTermYearEquals(aterm.getSemester(), aterm.getTermYear());
            /*if (0 < q.getResultList().size()) {
                aterm = aterm.merge();
            } else {
                aterm.persist();
            }*/
			if (0 >= q.getResultList().size()) {
				aterm.persist();
			}
			return aterm;
		}
	}
	
	private Major makeMajor(String amajor, String aterm) {
		Major major = new Major();
		Departments department = departmentMapper(amajor);
		major.setDepartment(department);
		Term term = doMakeTerm(aterm);
		major.setCatalogTerm(term);
		major.setCompleted_work_requirements(false);
		major.setReachedMilestone(false);
		return major;
	}
	
	private Departments departmentMapper(String major) {
		if (major.equals(FileDepartments.CAMBSCM.toString())) {
			return Departments.CAMBSCM;
		} else if (major.equals(FileDepartments.CAMPRE.toString())) {
			return Departments.CAMPRE;
		} else if (major.equals(FileDepartments.CBEVBSCM.toString())) {
			return Departments.CBEVBSCM;
		} else if (major.equals(FileDepartments.CBEVPRBSCM.toString())) {
			return Departments.CBEVPRBSCM;
		} else if (major.equals(FileDepartments.FDMBSHA.toString())) {
			return Departments.FDMBSHA;
		} else if (major.equals(FileDepartments.FDMPRE.toString())) {
			return Departments.FDMPRE;
		} else if (major.equals(FileDepartments.GAMBSGM.toString())) {
			return Departments.GAMBSGM;
		} else if (major.equals(FileDepartments.GAMPRE.toString())) {
			return Departments.GAMPRE;
		} else if (major.equals(FileDepartments.HBEVBSHA.toString())) {
			return Departments.HBEVBSHA;
		} else if (major.equals(FileDepartments.HBEVPRBSHA.toString())) {
			return Departments.HBEVPRBSHA;
		} else if (major.equals(FileDepartments.HOSSINBSMS.toString())) {
			return Departments.HOSSINBSMS;
		} else if (major.equals(FileDepartments.LRMBSHA.toString())) {
			return Departments.LRMBSHA;
		} else if (major.equals(FileDepartments.LRMPRE.toString())) {
			return Departments.LRMPRE;
		} else if (major.equals(FileDepartments.MEMBSHA.toString())) {
			return Departments.MEMBSHA;
		} else if (major.equals(FileDepartments.MEMPRE.toString())) {
			return Departments.MEMPRE;
		} else {
			throw new IllegalArgumentException("Invalid Major: " + major);
		}
	}
}

