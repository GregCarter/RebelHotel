package edu.unlv.cs.rebelhotel.service;

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

import edu.unlv.cs.rebelhotel.domain.Student;
import edu.unlv.cs.rebelhotel.form.FormStudentQuery;

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
			majorSearch.add(Restrictions.eq("department", formStudentQuery.getDepartment()));
			if (formStudentQuery.getUseMilestone()) {
				majorSearch.add(Restrictions.eq("reachedMilestone", formStudentQuery.getHasMilestone()));
			}
		}
		else if (formStudentQuery.getUseMilestone()) {
			search.createCriteria("majors")
			.add(Restrictions.eq("reachedMilestone", formStudentQuery.getHasMilestone()));
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
	
	public String buildPropertiesString(FormStudentQuery formStudentQuery) {
		String properties = "id";
		if (formStudentQuery.getShowUserId()) {
			properties += ",userId";
		}
		if (formStudentQuery.getShowEmail()) {
			properties += ",email";
		}
		if (formStudentQuery.getShowName()) {
			properties += ",name";
		}
		if (formStudentQuery.getShowAdmitTerm()) {
			properties += ",admitTerm";
		}
		if (formStudentQuery.getShowGradTerm()) {
			properties += ",gradTerm";
		}
		if (formStudentQuery.getShowCodeOfConductSigned()) {
			properties += ",codeOfConductSigned";
		}
		if (formStudentQuery.getShowLastModified()) {
			properties += ",lastModified";
		}
		if (formStudentQuery.getShowUserAccount()) {
			properties += ",userAccount";
		}
		/*if (properties.length() > 0) {
			properties = properties.substring(1);
		}*/
		return properties;
	}
	
	public String buildLabelsString(FormStudentQuery formStudentQuery) {
		// hackish method of getting locale messages ... but this service apparently is not in scope of the locale definitions necessary here
		MessageSource messageSource = SpringApplicationContext.getApplicationContext();
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
	
	public String buildMaxLengthsString(FormStudentQuery formStudentQuery) {
		// these will determine how many characters the table.jspx will display per data column; table.jspx defaults to 10, so this does too
		String properties = "10";
		if (formStudentQuery.getShowUserId()) {
			properties += ",10";
		}
		if (formStudentQuery.getShowEmail()) {
			properties += ",32";
		}
		if (formStudentQuery.getShowName()) {
			properties += ",64";
		}
		if (formStudentQuery.getShowAdmitTerm()) {
			properties += ",11";
		}
		if (formStudentQuery.getShowGradTerm()) {
			properties += ",11";
		}
		if (formStudentQuery.getShowCodeOfConductSigned()) {
			properties += ",10";
		}
		if (formStudentQuery.getShowLastModified()) {
			properties += ",32";
		}
		if (formStudentQuery.getShowUserAccount()) {
			properties += ",16";
		}
		return properties;
	}
}