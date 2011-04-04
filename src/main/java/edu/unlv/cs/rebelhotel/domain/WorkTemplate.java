package edu.unlv.cs.rebelhotel.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import edu.unlv.cs.rebelhotel.domain.Term;
import edu.unlv.cs.rebelhotel.domain.enums.Degree;

import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@RooJavaBean
//@RooToString
@RooEntity(finders = { "findWorkTemplatesByDegreeAndTermEquals" })
public class WorkTemplate {
	
	@Enumerated
	private Degree degree;

	@NotNull
    private String name;

    private Integer totalHoursNeeded;

    @ManyToOne
    private Term term;
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Degree: ").append(getDegree()).append(" ").append(getName());
        sb.append("TotalHoursNeeded: ").append(getTotalHoursNeeded()).append(", ");
        sb.append("Term: ").append(getTerm());
        return sb.toString();
    }
}
