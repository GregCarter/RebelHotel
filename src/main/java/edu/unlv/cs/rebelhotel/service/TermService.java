package edu.unlv.cs.rebelhotel.service;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import edu.unlv.cs.rebelhotel.domain.Term;
import edu.unlv.cs.rebelhotel.domain.enums.Semester;

@Service
public class TermService {
	public List<Term> generateCurrentTerms() {
		List<Term> result = new LinkedList<Term>();
		
		Session session = (Session) Term.entityManager().unwrap(Session.class);
		session.beginTransaction();
		
		Calendar now = Calendar.getInstance();
		Integer year = now.get(Calendar.YEAR);
		
		for (Semester semester : Semester.values()) {
			List queryResults = session.createCriteria(Term.class)
			.add(Restrictions.eq("termYear", year))
			.add(Restrictions.eq("semester", semester))
			.list();
			
			if (queryResults.size() == 0) {
				Term term = new Term(year, semester);
				term.persist();
				result.add(term);
			}
		}
		
		session.close();
		
		return result;
	}
}