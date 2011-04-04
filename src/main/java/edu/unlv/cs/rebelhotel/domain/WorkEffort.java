package edu.unlv.cs.rebelhotel.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import edu.unlv.cs.rebelhotel.domain.Student;
import javax.validation.constraints.NotNull;
import javax.persistence.ManyToOne;
import edu.unlv.cs.rebelhotel.domain.enums.Verification;
import edu.unlv.cs.rebelhotel.domain.Employer;
import edu.unlv.cs.rebelhotel.domain.Supervisor;
import javax.persistence.Embedded;
import edu.unlv.cs.rebelhotel.domain.WorkEffortDuration;
import edu.unlv.cs.rebelhotel.domain.enums.VerificationType;
import javax.persistence.Enumerated;
import edu.unlv.cs.rebelhotel.domain.enums.Validation;
import edu.unlv.cs.rebelhotel.domain.enums.PayStatus;

import java.util.Set;
import edu.unlv.cs.rebelhotel.domain.WorkRequirement;

import java.util.HashSet;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findWorkEffortsByStudentEquals" })
public class WorkEffort {
    //private static final Logger LOG = LoggerFactory.getLogger("audit");
	
    @NotNull
    @ManyToOne
    private Student student;

    private String workPosition;

    private String comment;

    @Embedded
    private Supervisor supervisor;

    @Embedded
    private Employer employer;

    @Enumerated
    private VerificationType verificationType;

    @Enumerated
    private Validation validation;

    @Enumerated
    private Verification verification;

    @Enumerated
    private PayStatus payStatus;

    @Embedded
    private WorkEffortDuration duration;

    @ManyToMany
    private Set<WorkRequirement> workRequirements = new HashSet<WorkRequirement>();
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Position: ").append(getWorkPosition()).append("\n");
        sb.append("At: ").append(getEmployer().getName()).append("\n");
        sb.append("Duration: ").append(getDuration()).append("\n").append("\n");
        return sb.toString();
    }
}
