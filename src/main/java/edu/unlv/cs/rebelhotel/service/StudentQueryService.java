package edu.unlv.cs.rebelhotel.service;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import au.com.bytecode.opencsv.CSVWriter;

import edu.unlv.cs.rebelhotel.domain.Student;
import edu.unlv.cs.rebelhotel.form.FormStudentQuery;
import edu.unlv.cs.rebelhotel.web.StudentController;

@Service
public class StudentQueryService {	
	public List<Student> queryStudents(FormStudentQuery formStudentQuery) {
		DetachedCriteria search = DetachedCriteria.forClass(Student.class);
		
		if (formStudentQuery.getUseUserId()) {
			search.add(Restrictions.eq("userId", formStudentQuery.getUserId()));
		}
		if (formStudentQuery.getUseModified()) {
			search.add(Restrictions.between("lastModified", formStudentQuery.getLastModifiedStart(), formStudentQuery.getLastModifiedEnd()));
		}
		if (formStudentQuery.getUseCatalogTerm()) {
			search.createCriteria("majors")
			.createCriteria("catalogTerm")
			.add(Example.create(formStudentQuery.getCatalogTerm()));
		}
		if (formStudentQuery.getUseGradTerm()) {
			search.createCriteria("gradTerm")
			.add(Example.create(formStudentQuery.getGradTerm()));
		}
		// If searching by major and milestone, find a major row entry that matches both
		// ie, a student with a major that has the milestone set
		// if searching by either a major or a milestone, then the restriction is lessened
		// ie, finding a student with at least one major that has a milestone, rather than
		// a student with a specific major with the milestone set
		if (formStudentQuery.getUseMajor()) {
			DetachedCriteria majorSearch = search.createCriteria("majors");
			majorSearch.add(Restrictions.eq("degree", formStudentQuery.getDegreeCode()));
			if (formStudentQuery.getUseMilestone()) {
				majorSearch.add(Restrictions.eq("reachedMilestone", formStudentQuery.getHasMilestone()));
			}
		}
		else if (formStudentQuery.getUseMilestone()) {
			search.createCriteria("majors")
			.add(Restrictions.eq("reachedMilestone", formStudentQuery.getHasMilestone()));
		}
		if (formStudentQuery.getUseFirstName()) {
			String firstName = formStudentQuery.getFirstName();
			if (firstName.length() > 0) {
				firstName = "%" + firstName + "%";
			}
			else {
				firstName = "%";
			}
			search.add(Restrictions.like("firstName", firstName));
		}
		if (formStudentQuery.getUseLastName()) {
			String lastName = formStudentQuery.getLastName();
			if (lastName.length() > 0) {
				lastName = "%" + lastName + "%";
			}
			else {
				lastName = "%";
			}
			search.add(Restrictions.like("lastName", lastName));
		}
		List students;
		
		DetachedCriteria rootQuery = DetachedCriteria.forClass(Student.class);
		search.setProjection(Projections.distinct(Projections.projectionList().add(Projections.alias(Projections.property("id"), "id"))));
		rootQuery.add(Subqueries.propertyIn("id", search));
		Session session = (Session) Student.entityManager().unwrap(Session.class);
		session.beginTransaction();
		students = rootQuery.getExecutableCriteria(session).list();
		session.close();
		
		return students;
	}
	
	public String buildLabelsString(FormStudentQuery formStudentQuery, MessageSource messageSource) {
		String properties = messageSource.getMessage("label_edu_unlv_cs_rebelhotel_domain_student_id", null, LocaleContextHolder.getLocale());
		if (formStudentQuery.getShowUserId()) {
			properties += "," + messageSource.getMessage("label_edu_unlv_cs_rebelhotel_domain_student_userid", null, LocaleContextHolder.getLocale());
		}
		if (formStudentQuery.getShowEmail()) {
			properties += "," + messageSource.getMessage("label_edu_unlv_cs_rebelhotel_domain_student_email", null, LocaleContextHolder.getLocale());
		}
		if (formStudentQuery.getShowName()) {
			// name is a "field" generated on the spot in the .jspx file
			properties += "," + messageSource.getMessage("label_edu_unlv_cs_rebelhotel_domain_student_name", null, LocaleContextHolder.getLocale());
		}
		if (formStudentQuery.getShowAdmitTerm()) {
			properties += "," + messageSource.getMessage("label_edu_unlv_cs_rebelhotel_domain_student_admitterm", null, LocaleContextHolder.getLocale());
		}
		if (formStudentQuery.getShowGradTerm()) {
			properties += "," + messageSource.getMessage("label_edu_unlv_cs_rebelhotel_domain_student_gradterm", null, LocaleContextHolder.getLocale());
		}
		if (formStudentQuery.getShowCodeOfConductSigned()) {
			properties += "," + messageSource.getMessage("label_edu_unlv_cs_rebelhotel_domain_student_codeofconductsigned", null, LocaleContextHolder.getLocale());
		}
		if (formStudentQuery.getShowLastModified()) {
			properties += "," + messageSource.getMessage("label_edu_unlv_cs_rebelhotel_domain_student_lastmodified", null, LocaleContextHolder.getLocale());
		}
		if (formStudentQuery.getShowUserAccount()) {
			properties += "," + messageSource.getMessage("label_edu_unlv_cs_rebelhotel_domain_student_useraccount", null, LocaleContextHolder.getLocale());
		}
		return properties;
	}
	
	public String generateCsv(FormStudentQuery formStudentQuery, List<Student> students, MessageSource messageSource) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		CSVWriter writer = new CSVWriter(new OutputStreamWriter(baos), ',');
		// commas cannot be in the locale messages in the comma-separated label string, just so you know
		String[] columns = buildLabelsString(formStudentQuery, messageSource).split(",");
		writer.writeNext(columns);
		for (Student student: students) {
			ArrayList<String> entries = new ArrayList();
			entries.add(student.getId().toString());
			if (formStudentQuery.getShowUserId()) {
				if (student.getUserId() != null) {
					entries.add(student.getUserId().toString());
				}
				else {
					entries.add("");
				}
			}
			if (formStudentQuery.getShowEmail()) {
				if (student.getEmail() != null) {
					entries.add(student.getEmail().toString());
				}
				else {
					entries.add("");
				}
			}
			if (formStudentQuery.getShowName()) {
				entries.add(student.getName());
			}
			if (formStudentQuery.getShowAdmitTerm()) {
				if (student.getAdmitTerm() != null) {
					entries.add(student.getAdmitTerm().toString());
				}
				else {
					entries.add("");
				}
			}
			if (formStudentQuery.getShowGradTerm()) {
				if (student.getGradTerm() != null) {
					entries.add(student.getGradTerm().toString());
				}
				else {
					entries.add("");
				}
			}
			if (formStudentQuery.getShowCodeOfConductSigned()) {
				if (student.getCodeOfConductSigned() != null) {
					entries.add(student.getCodeOfConductSigned().toString());
				}
				else {
					entries.add("");
				}
			}
			if (formStudentQuery.getShowLastModified()) {
				if (student.getLastModified() != null) {
					entries.add(student.getLastModified().toString());
				}
				else {
					entries.add("");
				}
			}
			if (formStudentQuery.getShowUserAccount()) {
				if (student.getUserAccount() != null) {
					entries.add(student.getUserAccount().toString());
				}
				else {
					entries.add("");
				}
			}
			String[] stringEntries = entries.toArray(new String[0]);
			writer.writeNext(stringEntries);
		}
		
		writer.flush();
		String contents = new String(baos.toByteArray());
		writer.close();
		return contents;
	}
}