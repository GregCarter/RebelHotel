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
}