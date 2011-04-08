package edu.unlv.cs.rebelhotel.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.persistence.CascadeType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;

import edu.unlv.cs.rebelhotel.domain.enums.Degree;
import edu.unlv.cs.rebelhotel.domain.WorkRequirement;
import edu.unlv.cs.rebelhotel.domain.Term;

@RooJavaBean
@RooEntity
public class Major {
	@OneToMany(cascade = CascadeType.ALL)
	private Set<WorkRequirement> workRequirements = new HashSet<WorkRequirement>();
	
	private boolean reachedMilestone;
	
	@Enumerated
	private Degree degree;

	@ManyToOne
    private Term catalogTerm;
	


	@Deprecated
	private boolean completed_work_requirements = false;
	
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getDegree()).append(", ");
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
		Major rhs = (Major) obj;
		return new EqualsBuilder().append(this, rhs).append(degree, rhs.degree)
				.append(catalogTerm, rhs.catalogTerm).isEquals();
	}

	@Override
	public int hashCode() {
		// you pick a hard-coded, randomly chosen, non-zero, odd number
		// ideally different for each class
		return new HashCodeBuilder(17, 37).append(degree).append(catalogTerm)
				.toHashCode();
	}
	
	public void initializeWorkRequirements() {
		List<WorkTemplate> workTemplates = WorkTemplate.findWorkTemplatesByDegreeAndTermEquals(getDegree(),getCatalogTerm()).getResultList();
		if (workTemplates.isEmpty()) {
			WorkTemplate wt = new WorkTemplate(this);
			wt.persist();
			workTemplates.add(wt);
		}
		
		for (WorkTemplate workTemplate : workTemplates) {
			WorkRequirement workRequirement = WorkRequirement.fromWorkTemplate(workTemplate, this);
			workRequirement.persist();
			addWorkRequirement(workRequirement);
		}
	}

	private void addWorkRequirement(WorkRequirement workRequirement) {
		getWorkRequirements().add(workRequirement);
	}
}
