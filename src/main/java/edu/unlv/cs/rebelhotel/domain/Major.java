package edu.unlv.cs.rebelhotel.domain;

import java.util.HashSet;
import java.util.Set;

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
@RooToString
@RooEntity
public class Major {
	@ManyToMany(cascade = CascadeType.ALL)
	private Set<WorkRequirement> workRequirements = new HashSet<WorkRequirement>();
	
	private boolean reachedMilestone;
	
	@Enumerated
	private Departments department;

	@ManyToOne
    private Term catalogTerm;

}
