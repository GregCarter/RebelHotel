package edu.unlv.cs.rebelhotel.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import edu.unlv.cs.rebelhotel.domain.enums.Semester;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.persistence.UniqueConstraint;

@RooJavaBean
@RooToString
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "semester", "termYear" }) })
@RooEntity(finders = { "findTermsBySemester" })
public class Term {

    @NotNull
    private Integer termYear;

    @Enumerated
    private Semester semester;

    public Term(Integer termYear, Semester semester) {
        this.termYear = termYear;
        this.semester = semester;
    }

    public Term() {
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getSemester() + " " + getTermYear());
        return sb.toString();
    }

    public static Boolean doesExist(Semester semester, Integer termYear) {
        if (null == semester) {
            throw new IllegalArgumentException("Must specify a emester.");
        } else if (null == termYear || 0 == termYear) {
            throw new IllegalArgumentException("Must specify a term year.");
        } else {
            EntityManager em = Term.entityManager();
            TypedQuery<Term> q = em.createQuery("SELECT Term FROM Term AS term WHERE term.semseter = :semester AND term.termYear = :termYear", Term.class);
            q.setParameter("semester", semester);
            q.setParameter("termYear", termYear);
            if (0 < q.getResultList().size()) {
                return true;
            } else {
                return false;
            }
        }
    }
}
