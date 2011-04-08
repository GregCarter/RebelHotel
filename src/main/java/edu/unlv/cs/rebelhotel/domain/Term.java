package edu.unlv.cs.rebelhotel.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import edu.unlv.cs.rebelhotel.domain.enums.Semester;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@RooJavaBean
@RooToString
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "semester", "termYear" }) })
@RooEntity(finders = { "findTermsBySemester", "findTermsBySemesterAndTermYearEquals" })
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
    
    @Override
    public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		Term rhs = (Term) obj;
		return new EqualsBuilder()
				.append(this, rhs)
				.append(semester, rhs.semester)
				.append(termYear, rhs.termYear)
				.isEquals();
    }
    
    @Override
    public int hashCode() {
		// you pick a hard-coded, randomly chosen, non-zero, odd number
		// ideally different for each class
		return new HashCodeBuilder(27, 99)
				.append(semester)
				.append(termYear)
				.toHashCode();
    }
}
