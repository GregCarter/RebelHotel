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
	
	public static final Integer DEFAULT_TOTAL_HOURS_NEEDED = 500;

	@Enumerated
	private Degree degree;

	@NotNull
    private String name;

    private Integer totalHoursNeeded;

    @ManyToOne
    private Term term;
    
    public WorkTemplate(){}
    
    public WorkTemplate(Major major) {
    	this.degree = major.getDegree();
    	this.term = major.getCatalogTerm();
    	this.name = "";
    	this.totalHoursNeeded = DEFAULT_TOTAL_HOURS_NEEDED;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Degree: ").append(getDegree()).append(" ").append(getName());
        sb.append("TotalHoursNeeded: ").append(getTotalHoursNeeded()).append(", ");
        sb.append("Term: ").append(getTerm());
        return sb.toString();
    }
}
