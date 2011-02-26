package edu.unlv.cs.rebelhotel.domain;

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
@RooEntity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"semester", "termYear"})})
public class Term {

	@NotNull
    private Integer termYear;

    @Enumerated
    private Semester semester;
    
    public Term(Integer termYear, Semester semester) {
    	this.termYear = termYear;
    	this.semester = semester;
    }
    
    public Term() { // empty parameter constructor for compatibility in Term_Roo_Entity
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getSemester()+" "+getTermYear());
        return sb.toString();
    }
}
