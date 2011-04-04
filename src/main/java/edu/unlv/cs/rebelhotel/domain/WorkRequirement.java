package edu.unlv.cs.rebelhotel.domain;

import java.util.HashSet;
import java.util.Set;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;

//import javax.persistence.CascadeType;
//import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
@RooJavaBean
@RooToString
@RooEntity
public class WorkRequirement {
	@ManyToOne
	private WorkTemplate workTemplate;
	
    @NotNull
    private String name;

    //private Integer hours;

    private boolean milestone;
    
    /*@ManyToOne
    private Major major;*/
    
    @ManyToMany
    private Set<WorkEffort> workEffort = new HashSet<WorkEffort>();
    
    // A method to construct a work requirement from a work template
    public static WorkRequirement fromWorkTemplate(WorkTemplate wt, Major major) {
    	WorkRequirement wr = new WorkRequirement();
    	//wr.setHours(wt.getTotalHoursNeeded());
    	wr.setWorkTemplate(wt);
    	wr.setName(wt.getName());
    	wr.setMilestone(false);
    	//wr.setMajor(major);
    	return wr;
    }
}

