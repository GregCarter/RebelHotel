package edu.unlv.cs.rebelhotel.domain;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;

import edu.unlv.cs.rebelhotel.domain.Term;

@RooJavaBean
@RooEntity
public class Major {
	private boolean reachedMilestone;
	
	@NotNull
	private String degreeCode;

	@ManyToOne
    private Term catalogTerm;

	@Deprecated
	private boolean completed_work_requirements = false;
	
	public Major(){}
	
	public Major(String degreeCode, Term catalogTerm) {
		this.degreeCode = degreeCode;
		this.catalogTerm = catalogTerm;
		this.reachedMilestone = false;
	}
	
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getDegreeCode()).append(", ");
        return sb.toString();
    }
}
