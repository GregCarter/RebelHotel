package edu.unlv.cs.rebelhotel.service;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import au.com.bytecode.opencsv.CSVWriter;

import edu.unlv.cs.rebelhotel.domain.Student;
import edu.unlv.cs.rebelhotel.domain.WorkEffort;
import edu.unlv.cs.rebelhotel.domain.enums.Verification;
import edu.unlv.cs.rebelhotel.form.FormStudentQuery;
import edu.unlv.cs.rebelhotel.web.StudentController;

@Service
public class StudentQueryService {	
	public List<Object> queryStudents(FormStudentQuery formStudentQuery, String sorting) {
DetachedCriteria search = DetachedCriteria.forClass(Student.class, "oq");
		
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
		if (formStudentQuery.getUseMajor() || formStudentQuery.getUseMilestone() || formStudentQuery.getStudentUseHours()) {
			DetachedCriteria majorSearch = search.createCriteria("majors");
			if (formStudentQuery.getStudentUseHours()) {
				DetachedCriteria weiq = DetachedCriteria.forClass(Student.class, "iq")
				.createAlias("majors", "major")
				.setProjection(Projections.projectionList()
						.add(Projections.max("major.totalHours")))
						.add(Restrictions.eqProperty("iq.id", "oq.id"));
				if (formStudentQuery.getUseMajor()) {
					weiq.add(Restrictions.eq("major.degreeCode", formStudentQuery.getDegreeCode()));
				}
				if (formStudentQuery.getUseMilestone()) {
					weiq.add(Restrictions.eq("major.reachedMilestone", formStudentQuery.getHasMilestone()));
				}
				
				if (formStudentQuery.getStudentHoursLow() != null) {
					majorSearch.add(Subqueries.le(new Long(formStudentQuery.getStudentHoursLow()), weiq));
				}
				if (formStudentQuery.getStudentHoursHigh() != null) {
					majorSearch.add(Subqueries.ge(new Long(formStudentQuery.getStudentHoursHigh()), weiq));
				}
			}
			else {
				if (formStudentQuery.getUseMajor()) {
					majorSearch.add(Restrictions.eq("major.degreeCode", formStudentQuery.getDegreeCode()));
				}
				if (formStudentQuery.getUseMilestone()) {
					majorSearch.add(Restrictions.eq("major.reachedMilestone", formStudentQuery.getHasMilestone()));
				}
			}
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
		if (formStudentQuery.getUseMiddleName()) {
			String middleName = formStudentQuery.getMiddleName();
			if (middleName.length() > 0) {
				middleName = "%" + middleName + "%";
			}
			else {
				middleName = "%";
			}
			search.add(Restrictions.like("middleName", middleName));
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
		
		// this is so grossly inefficient that it should be replaced with an HQL query or a "totalHours" property should be stored on students
		// left in for now because it has the correct functionality
		if (formStudentQuery.getUseHours()) {
			DetachedCriteria weiq = DetachedCriteria.forClass(Student.class, "iq")
			.createAlias("workEffort", "we")
			.setProjection(Projections.projectionList()
					.add(Projections.sum("we.duration.hours")))
					.add(Restrictions.eqProperty("iq.id", "oq.id"));
			if (formStudentQuery.getValidationSelected()) {
				weiq.add(Restrictions.eq("we.validation", formStudentQuery.getValidation()));
			}
			if (formStudentQuery.getVerificationSelected()) {
				weiq.add(Restrictions.eq("we.verification", formStudentQuery.getVerification()));
			}
			
			search.createAlias("workEffort", "owe")
			.setProjection(Projections.projectionList()
					.add(Projections.sum("owe.duration.hours").as("totalHours"))
					.add(Projections.groupProperty("id"))
					.add(Projections.property("id")))
			.setResultTransformer(Transformers.aliasToBean(Student.class)); // this will have no visual effect due to "search" being a subquery
			if (formStudentQuery.getValidationSelected()) {
				search.add(Restrictions.eq("owe.validation", formStudentQuery.getValidation()));
			}
			if (formStudentQuery.getVerificationSelected()) {
				search.add(Restrictions.eq("owe.verification", formStudentQuery.getVerification()));
			}
			if (formStudentQuery.getHoursLow() != null) {
				search.add(Subqueries.le(new Long(formStudentQuery.getHoursLow()), weiq));
			}
			if (formStudentQuery.getHoursHigh() != null) {
				search.add(Subqueries.ge(new Long(formStudentQuery.getHoursHigh()), weiq));
			}
			if (formStudentQuery.getEmployerName() != "") {
				search.add(Restrictions.like("owe.employer.name", "%" + formStudentQuery.getEmployerName() + "%"));
			}
			if (formStudentQuery.getEmployerLocation() != "") {
				search.add(Restrictions.like("owe.employer.location", "%" + formStudentQuery.getEmployerLocation() + "%"));
			}
			if (formStudentQuery.getWorkEffortStartDate() != null) {
				search.add(Restrictions.ge("owe.duration.startDate", formStudentQuery.getWorkEffortStartDate()));
			}
			if (formStudentQuery.getWorkEffortEndDate() != null) {
				search.add(Restrictions.le("owe.duration.endDate", formStudentQuery.getWorkEffortEndDate()));
			}
		}
		else {
			DetachedCriteria lq = search.createCriteria("workEffort");
			if (formStudentQuery.getVerificationSelected()) {
				lq.add(Restrictions.eq("verification", formStudentQuery.getVerification()));
			}
			if (formStudentQuery.getValidationSelected()) {
				lq.add(Restrictions.eq("validation", formStudentQuery.getValidation()));
			}
			if (formStudentQuery.getEmployerName() != "") {
				lq.add(Restrictions.like("owe.employer.name", "%" + formStudentQuery.getEmployerName() + "%"));
			}
			if (formStudentQuery.getEmployerLocation() != "") {
				lq.add(Restrictions.like("owe.employer.location", "%" + formStudentQuery.getEmployerLocation() + "%"));
			}
			if (formStudentQuery.getWorkEffortStartDate() != null) {
				lq.add(Restrictions.ge("owe.duration.startDate", formStudentQuery.getWorkEffortStartDate()));
			}
			if (formStudentQuery.getWorkEffortEndDate() != null) {
				lq.add(Restrictions.le("owe.duration.endDate", formStudentQuery.getWorkEffortEndDate()));
			}
		}
		
		List students;
		
		DetachedCriteria rootQuery = DetachedCriteria.forClass(Student.class);
		search.setProjection(Projections.distinct(Projections.projectionList().add(Projections.alias(Projections.property("id"), "id"))));
		rootQuery.add(Subqueries.propertyIn("id", search));
		DetachedCriteria countQuery = DetachedCriteria.forClass(Student.class);
		countQuery.add(Subqueries.propertyIn("id", search));
		countQuery.setProjection(Projections.rowCount());
		
		if (sorting != null) {
			if (sorting != "") {
				int sort_value = Integer.parseInt(sorting.trim());
				String property = getPropertyFromIndex(formStudentQuery, sort_value);
				if (sort_value % 2 == 0) {
					rootQuery.addOrder(Order.asc(property));
					
				}
				else {
					rootQuery.addOrder(Order.desc(property));
				}
			}
		}
		
		Session session = (Session) Student.entityManager().unwrap(Session.class);
		session.beginTransaction();
		Criteria query = rootQuery.getExecutableCriteria(session);
		students = query.list();
		Long count = (Long) countQuery.getExecutableCriteria(session).list().get(0);
		session.close();
		
		List<Object> resultList = new LinkedList<Object>();
		resultList.add(count);
		resultList.add(students);
		
		return resultList;
	}
	
	public List<Object> queryStudentsLimited(FormStudentQuery formStudentQuery, String sorting, Integer start, Integer size) {
		DetachedCriteria search = DetachedCriteria.forClass(Student.class, "oq");
		
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
		if (formStudentQuery.getUseMajor() || formStudentQuery.getUseMilestone() || formStudentQuery.getStudentUseHours()) {
			DetachedCriteria majorSearch = search.createCriteria("majors");
			if (formStudentQuery.getStudentUseHours()) {
				DetachedCriteria weiq = DetachedCriteria.forClass(Student.class, "iq")
				.createAlias("majors", "major")
				.setProjection(Projections.projectionList()
						.add(Projections.max("major.totalHours")))
						.add(Restrictions.eqProperty("iq.id", "oq.id"));
				if (formStudentQuery.getUseMajor()) {
					weiq.add(Restrictions.eq("major.degreeCode", formStudentQuery.getDegreeCode()));
				}
				if (formStudentQuery.getUseMilestone()) {
					weiq.add(Restrictions.eq("major.reachedMilestone", formStudentQuery.getHasMilestone()));
				}
				
				if (formStudentQuery.getStudentHoursLow() != null) {
					majorSearch.add(Subqueries.le(new Long(formStudentQuery.getStudentHoursLow()), weiq));
				}
				if (formStudentQuery.getStudentHoursHigh() != null) {
					majorSearch.add(Subqueries.ge(new Long(formStudentQuery.getStudentHoursHigh()), weiq));
				}
			}
			else {
				if (formStudentQuery.getUseMajor()) {
					majorSearch.add(Restrictions.eq("major.degreeCode", formStudentQuery.getDegreeCode()));
				}
				if (formStudentQuery.getUseMilestone()) {
					majorSearch.add(Restrictions.eq("major.reachedMilestone", formStudentQuery.getHasMilestone()));
				}
			}
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
		if (formStudentQuery.getUseMiddleName()) {
			String middleName = formStudentQuery.getMiddleName();
			if (middleName.length() > 0) {
				middleName = "%" + middleName + "%";
			}
			else {
				middleName = "%";
			}
			search.add(Restrictions.like("middleName", middleName));
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
		
		// this is so grossly inefficient that it should be replaced with an HQL query or a "totalHours" property should be stored on students
		// left in for now because it has the correct functionality
		if (formStudentQuery.getUseHours()) {
			DetachedCriteria weiq = DetachedCriteria.forClass(Student.class, "iq")
			.createAlias("workEffort", "we")
			.setProjection(Projections.projectionList()
					.add(Projections.sum("we.duration.hours")))
					.add(Restrictions.eqProperty("iq.id", "oq.id"));
			if (formStudentQuery.getValidationSelected()) {
				weiq.add(Restrictions.eq("we.validation", formStudentQuery.getValidation()));
			}
			if (formStudentQuery.getVerificationSelected()) {
				weiq.add(Restrictions.eq("we.verification", formStudentQuery.getVerification()));
			}
			
			search.createAlias("workEffort", "owe")
			.setProjection(Projections.projectionList()
					.add(Projections.sum("owe.duration.hours").as("totalHours"))
					.add(Projections.groupProperty("id"))
					.add(Projections.property("id")))
			.setResultTransformer(Transformers.aliasToBean(Student.class)); // this will have no visual effect due to "search" being a subquery
			if (formStudentQuery.getValidationSelected()) {
				search.add(Restrictions.eq("owe.validation", formStudentQuery.getValidation()));
			}
			if (formStudentQuery.getVerificationSelected()) {
				search.add(Restrictions.eq("owe.verification", formStudentQuery.getVerification()));
			}
			if (formStudentQuery.getHoursLow() != null) {
				search.add(Subqueries.le(new Long(formStudentQuery.getHoursLow()), weiq));
			}
			if (formStudentQuery.getHoursHigh() != null) {
				search.add(Subqueries.ge(new Long(formStudentQuery.getHoursHigh()), weiq));
			}
			if (formStudentQuery.getEmployerName() != "") {
				search.add(Restrictions.like("owe.employer.name", "%" + formStudentQuery.getEmployerName() + "%"));
			}
			if (formStudentQuery.getEmployerLocation() != "") {
				search.add(Restrictions.like("owe.employer.location", "%" + formStudentQuery.getEmployerLocation() + "%"));
			}
			if (formStudentQuery.getWorkEffortStartDate() != null) {
				search.add(Restrictions.ge("owe.duration.startDate", formStudentQuery.getWorkEffortStartDate()));
			}
			if (formStudentQuery.getWorkEffortEndDate() != null) {
				search.add(Restrictions.le("owe.duration.endDate", formStudentQuery.getWorkEffortEndDate()));
			}
		}
		else {
			DetachedCriteria lq = search.createCriteria("workEffort");
			if (formStudentQuery.getVerificationSelected()) {
				lq.add(Restrictions.eq("verification", formStudentQuery.getVerification()));
			}
			if (formStudentQuery.getValidationSelected()) {
				lq.add(Restrictions.eq("validation", formStudentQuery.getValidation()));
			}
			if (formStudentQuery.getEmployerName() != "") {
				lq.add(Restrictions.like("owe.employer.name", "%" + formStudentQuery.getEmployerName() + "%"));
			}
			if (formStudentQuery.getEmployerLocation() != "") {
				lq.add(Restrictions.like("owe.employer.location", "%" + formStudentQuery.getEmployerLocation() + "%"));
			}
			if (formStudentQuery.getWorkEffortStartDate() != null) {
				lq.add(Restrictions.ge("owe.duration.startDate", formStudentQuery.getWorkEffortStartDate()));
			}
			if (formStudentQuery.getWorkEffortEndDate() != null) {
				lq.add(Restrictions.le("owe.duration.endDate", formStudentQuery.getWorkEffortEndDate()));
			}
		}
		
		List students;
		
		DetachedCriteria rootQuery = DetachedCriteria.forClass(Student.class);
		search.setProjection(Projections.distinct(Projections.projectionList().add(Projections.alias(Projections.property("id"), "id"))));
		rootQuery.add(Subqueries.propertyIn("id", search));
		DetachedCriteria countQuery = DetachedCriteria.forClass(Student.class);
		countQuery.add(Subqueries.propertyIn("id", search));
		countQuery.setProjection(Projections.rowCount());
		
		if (sorting != null) {
			if (sorting != "") {
				int sort_value = Integer.parseInt(sorting.trim());
				String property = getPropertyFromIndex(formStudentQuery, sort_value);
				if (sort_value % 2 == 0) {
					rootQuery.addOrder(Order.asc(property));
					
				}
				else {
					rootQuery.addOrder(Order.desc(property));
				}
			}
		}
		
		Session session = (Session) Student.entityManager().unwrap(Session.class);
		session.beginTransaction();
		Criteria query = rootQuery.getExecutableCriteria(session);
		query.setFirstResult(start);
		query.setMaxResults(size);
		students = query.list();
		Long count = (Long) countQuery.getExecutableCriteria(session).list().get(0);
		session.close();
		
		List<Object> resultList = new LinkedList<Object>();
		resultList.add(count);
		resultList.add(students);
		
		return resultList;
	}
	
	public String buildLabelsString(FormStudentQuery formStudentQuery, MessageSource messageSource) {
		String properties = messageSource.getMessage("label_edu_unlv_cs_rebelhotel_domain_student_id", null, LocaleContextHolder.getLocale());
		if (formStudentQuery.getShowUserId()) {
			properties += "," + messageSource.getMessage("label_edu_unlv_cs_rebelhotel_domain_student_userid", null, LocaleContextHolder.getLocale());
		}
		if (formStudentQuery.getShowEmail()) {
			properties += "," + messageSource.getMessage("label_edu_unlv_cs_rebelhotel_domain_student_email", null, LocaleContextHolder.getLocale());
		}
		if (formStudentQuery.getShowFirstName()) {
			properties += "," + messageSource.getMessage("label_edu_unlv_cs_rebelhotel_domain_student_firstname", null, LocaleContextHolder.getLocale());
		}
		if (formStudentQuery.getShowMiddleName()) {
			properties += "," + messageSource.getMessage("label_edu_unlv_cs_rebelhotel_domain_student_middlename", null, LocaleContextHolder.getLocale());
		}
		if (formStudentQuery.getShowLastName()) {
			properties += "," + messageSource.getMessage("label_edu_unlv_cs_rebelhotel_domain_student_lastname", null, LocaleContextHolder.getLocale());
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
	
	public String getPropertyFromIndex(FormStudentQuery formStudentQuery, Integer index) {
		index = index / 2;
		Integer iterator = new Integer(0);
		if (index == 0) {
			return "id";
		}
		iterator = iterator + 1;
		if (formStudentQuery.getShowUserId()) {
			if (iterator == index) {
				return "userId";
			}
			iterator = iterator + 1;
		}
		if (formStudentQuery.getShowEmail()) {
			if (iterator == index) {
				return "email";
			}
			iterator = iterator + 1;
		}
		if (formStudentQuery.getShowFirstName()) {
			if (iterator == index) {
				return "firstName";
			}
			iterator = iterator + 1;
		}
		if (formStudentQuery.getShowMiddleName()) {
			if (iterator == index) {
				return "middleName";
			}
			iterator = iterator + 1;
		}
		if (formStudentQuery.getShowLastName()) {
			if (iterator == index) {
				return "lastName";
			}
			iterator = iterator + 1;
		}
		if (formStudentQuery.getShowAdmitTerm()) {
			if (iterator == index) {
				return "admitTerm";
			}
			iterator = iterator + 1;
		}
		if (formStudentQuery.getShowGradTerm()) {
			if (iterator == index) {
				return "gradTerm";
			}
			iterator = iterator + 1;
		}
		if (formStudentQuery.getShowCodeOfConductSigned()) {
			if (iterator == index) {
				return "codeOfConductSigned";
			}
			iterator = iterator + 1;
		}
		if (formStudentQuery.getShowLastModified()) {
			if (iterator == index) {
				return "lastModified";
			}
			iterator = iterator + 1;
		}
		if (formStudentQuery.getShowUserAccount()) {
			if (iterator == index) {
				return "userAccount";
			}
			iterator = iterator + 1;
		}
		
		return "invalid index"; // likely will want to throw an exception instead
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
			if (formStudentQuery.getShowFirstName()) {
				entries.add(student.getFirstName());
			}
			if (formStudentQuery.getShowMiddleName()) {
				entries.add(student.getMiddleName());
			}
			if (formStudentQuery.getShowLastName()) {
				entries.add(student.getLastName());
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