package edu.unlv.cs.rebelhotel.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
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

@RooJavaBean
@RooToString
@RooEntity(finders = { "findWorkEffortsByStudentEquals" })
public class WorkEffort {

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

    @ManyToOne
    private WorkEffortDuration duration;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<WorkRequirement> workRequirements = new HashSet<WorkRequirement>();
}
