package edu.unlv.cs.rebelhotel.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.util.Set;
import edu.unlv.cs.rebelhotel.domain.WorkRequirement;
import java.util.HashSet;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import edu.unlv.cs.rebelhotel.domain.Term;
import edu.unlv.cs.rebelhotel.domain.WorkEffort;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.persistence.Embedded;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findStudentsByMajor1Equals", "findStudentsByFirstNameEquals", "findStudentsByFirstNameLike", "findStudentsByMajor2Equals", "findStudentsByUserAccount" })
public class Student {
	@NotNull
	@Column(unique = true)
	private String userId;

    @NotNull
    @Size(min = 2)
    private String email;

    @NotNull
    @Size(min = 2)
    private String firstName;

    private String middleName;

    private String lastName;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<WorkRequirement> workRequirements = new HashSet<WorkRequirement>();

    @Size(min = 2)
    private String major1;

    private String major2;

    @ManyToOne
    private Term admitTerm;

    @ManyToOne
    private Term gradTerm;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<WorkEffort> workEffort = new HashSet<WorkEffort>();

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<ViewProgress> milestone = new HashSet<ViewProgress>();
    
    @OneToOne(optional = false)
    private UserAccount userAccount;
    
    public boolean hasReachedMilestone() {
    	boolean reachedMilestone = true;
    	while (reachedMilestone) {
    		for (ViewProgress each : milestone) {
    			if(each.getRemainingHours()!=0) {
    				reachedMilestone = false;
    			}
    		}
    	}
    	return reachedMilestone;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(" + getUserId() + ")");
        sb.append(" "+getFirstName());
        if (getLastName() != null) {
        	sb.append(" "+getLastName());
        }
        return sb.toString();
    }
    
    public void addWorkEffort(WorkEffort we) {
    	workEffort.add(we);
    }
}

