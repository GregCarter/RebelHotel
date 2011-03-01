package edu.unlv.cs.rebelhotel.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import edu.unlv.cs.rebelhotel.domain.Student;
import edu.unlv.cs.rebelhotel.form.FormStudentQuery;

@Service
public class StudentQueryService {
	public List<Student> queryStudents(FormStudentQuery formStudentQuery) {
		Session session = (Session) Student.entityManager().unwrap(Session.class);
		session.beginTransaction();
		Criteria search = session.createCriteria(Student.class);
		if (formStudentQuery.getUseUserId() == true) {
			search.add(Restrictions.eq("userId", formStudentQuery.getUserId()));
		}
		List students = search.list();
		session.close();
		
		return students;
	}
}