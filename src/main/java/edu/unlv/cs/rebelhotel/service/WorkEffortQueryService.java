package edu.unlv.cs.rebelhotel.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import edu.unlv.cs.rebelhotel.domain.Student;
import edu.unlv.cs.rebelhotel.domain.Employer;
import edu.unlv.cs.rebelhotel.domain.WorkEffort;
import edu.unlv.cs.rebelhotel.form.FormStudentQuery;
import edu.unlv.cs.rebelhotel.form.FormWorkEffortQuery;

@Service
public class WorkEffortQueryService {

	public List<WorkEffort> queryWorkEfforts(FormWorkEffortQuery fweq) {

		DetachedCriteria search = DetachedCriteria.forClass(WorkEffort.class);
		search.createAlias("student", "student");
		// look for non-empty fields here

		if (fweq.getUserId() != "") {
			search.add(Restrictions.eq("student.userId", fweq.getUserId()));
		}
		if (fweq.getStudentFirstName() != "") {

			search.add(Restrictions.eq("student.firstName",
					fweq.getStudentFirstName()));
		}
		if (fweq.getStudentMiddleName() != "") {
			search.add(Restrictions.eq("student.middleName",
					fweq.getStudentMiddleName()));

		}
		if (fweq.getStudentLastName() != "") {
			search.add(Restrictions.eq("student.lastName",
					fweq.getStudentLastName()));

		}
		if (fweq.getEmployerName() != "") {
			search.add(Restrictions.eq("employer.name", fweq.getEmployerName()));
		}
		if (fweq.getEmployerLocation() != "") {
			search.add(Restrictions.eq("employer.location",
					fweq.getEmployerLocation()));
		}

		if (fweq.isValidationSelected()) {
			search.add(Restrictions.eq("validation", fweq.getValidation()));
		}
		if (fweq.getStartDate() != null) {
			search.add(Restrictions.ge("duration.endDate", fweq.getStartDate()));
		}
		if (fweq.getEndDate() != null) {
			search.add(Restrictions.le("duration.startDate", fweq.getEndDate()));
		}
		List workefforts;

		DetachedCriteria rootQuery = DetachedCriteria
				.forClass(WorkEffort.class);
		search.setProjection(Projections.distinct(Projections.projectionList()
				.add(Projections.alias(Projections.property("id"), "id"))));
		rootQuery.add(Subqueries.propertyIn("id", search));
		Session session = (Session) Student.entityManager().unwrap(
				Session.class);
		session.beginTransaction();
		workefforts = rootQuery.getExecutableCriteria(session).list();
		session.close();

		return workefforts;

	}

	public String buildPropertiesString() {
		String properties = "student";
		properties += ",workPosition";
		properties += ",employer";
		properties += ",verificationType";
		properties += ",validation";
		/*
		 * if (properties.length() > 0) { properties = properties.substring(1);
		 * }
		 */
		return properties;
	}

	public String buildLabelsString() {
		// hackish method of getting locale messages ... but this service
		// apparently is not in scope of the locale definitions necessary here
		MessageSource messageSource = SpringApplicationContext
				.getApplicationContext();

		String properties = messageSource.getMessage(
				"label_edu_unlv_cs_rebelhotel_domain_student", null,
				LocaleContextHolder.getLocale());

		properties += ","
				+ messageSource
						.getMessage(
								"label_edu_unlv_cs_rebelhotel_form_formworkeffort_workposition",
								null, LocaleContextHolder.getLocale());

		properties += ","
				+ messageSource
						.getMessage(
								"label_edu_unlv_cs_rebelhotel_domain_workeffort_employer",
								null, LocaleContextHolder.getLocale());

		properties += ","
				+ messageSource
						.getMessage(
								"label_edu_unlv_cs_rebelhotel_form_formworkeffort_verificationtype",
								null, LocaleContextHolder.getLocale());
		properties += ","
				+ messageSource
						.getMessage(
								"label_edu_unlv_cs_rebelhotel_form_formworkeffort_validation",
								null, LocaleContextHolder.getLocale());
		return properties;
	}

	public String buildMaxLengthsString() {
		// these will determine how many characters the table.jspx will display
		// per data column; table.jspx defaults to 10, so this does too
		String properties = "35";

		properties += ",30";

		properties += ",30";

		properties += ",20";

		properties += ",20";

		return properties;
	}

}
