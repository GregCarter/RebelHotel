package edu.unlv.cs.rebelhotel.domain;

import java.util.HashSet;
import java.util.Set;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import edu.unlv.cs.rebelhotel.domain.Student;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
@RooJavaBean
@RooToString
@RooEntity
public class WorkRequirement {

    @NotNull
    private String name;

    private Integer hours;

    @ManyToOne
    private Student student;
    
    @ManyToMany(cascade = CascadeType.ALL, mappedBy="workRequirements")
    private Set<WorkEffort> workEffort = new HashSet<WorkEffort>();
    
    // A method to construct a work requirement from a work template
    public static WorkRequirement fromWorkTemplate(WorkTemplate wt, Student student) {
    	WorkRequirement wr = new WorkRequirement();
    	wr.setHours(wt.getHours());
    	wr.setName(wt.getName());
    	wr.setStudent(student);
    	return wr;
    }
}
