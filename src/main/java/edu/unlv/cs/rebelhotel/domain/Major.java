package edu.unlv.cs.rebelhotel.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import edu.unlv.cs.rebelhotel.domain.enums.Departments;

@RooJavaBean
@RooToString
@RooEntity
public class Major {
	
	@ManyToMany
	private Set<WorkRequirement> workRequirements = new HashSet<WorkRequirement>();
	// QUESTION: are we making it a set because it will contain
	// both general and major specific?
	// ANSWER: 
	
	private boolean reachedMilestone;
	
	@Enumerated
	private Departments department;
	
    @ManyToOne
    private Term catalogTerm;

}
