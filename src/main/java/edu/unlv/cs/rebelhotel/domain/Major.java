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

import edu.unlv.cs.rebelhotel.domain.enums.Degree;
import edu.unlv.cs.rebelhotel.domain.WorkRequirement;
import edu.unlv.cs.rebelhotel.domain.Term;

@RooJavaBean
@RooEntity
public class Major {
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<WorkRequirement> workRequirements = new HashSet<WorkRequirement>();
	
	private boolean reachedMilestone;
	
	@Enumerated
	private Degree degree;

	@ManyToOne
    private Term catalogTerm;

	private boolean completed_work_requirements = false;
	
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Degree str = getDegree();
        StringTokenizer st = new StringTokenizer(str.toString(),"_");
        while (st.hasMoreTokens()) {
        	sb.append(st.nextToken()).append(" ");
        }
        return sb.toString();
    }
}
