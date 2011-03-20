package edu.unlv.cs.rebelhotel.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.persistence.CascadeType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import edu.unlv.cs.rebelhotel.domain.enums.Departments;
import edu.unlv.cs.rebelhotel.domain.WorkRequirement;
import edu.unlv.cs.rebelhotel.domain.Term;

@RooJavaBean
@RooEntity
public class Major {
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<WorkRequirement> workRequirements = new HashSet<WorkRequirement>();
	
	private boolean reachedMilestone;
	
	@Enumerated
	private Departments department;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Term catalogTerm;

	private boolean completed_work_requirements = false;
	
    public String toString() {
        StringBuilder sb = new StringBuilder();
        /*sb.append("WorkRequirements: ").append(getWorkRequirements() == null ? "null" : getWorkRequirements().size()).append(", ");
        sb.append("ReachedMilestone: ").append(isReachedMilestone()).append(", ");*/
        Departments str = getDepartment();
        StringTokenizer st = new StringTokenizer(str.toString(),"_");
        while (st.hasMoreTokens()) {
        	sb.append(st.nextToken()).append(" ");
        }
        //sb.append(", ");
        /*sb.append("CatalogTerm: ").append(getCatalogTerm()).append(", ");
        sb.append("Completed_work_requirements: ").append(isCompleted_work_requirements());*/
        return sb.toString();
    }
	
}
