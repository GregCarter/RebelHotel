package edu.unlv.cs.rebelhotel.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import edu.unlv.cs.rebelhotel.domain.Student;
import javax.validation.constraints.NotNull;
import javax.persistence.ManyToOne;
import edu.unlv.cs.rebelhotel.domain.Verification;
import edu.unlv.cs.rebelhotel.domain.Employer;
import edu.unlv.cs.rebelhotel.domain.Supervisor;
import javax.persistence.Embedded;
import edu.unlv.cs.rebelhotel.domain.WorkEffortDuration;

@RooJavaBean
@RooToString
@RooEntity
public class WorkEffort {

    @NotNull
    @ManyToOne
    private Student Student;

    private Integer hours;

    private String workPosition;

    private Verification verification;

    private String comment;

    @Embedded
    private Supervisor supervisor;

    @Embedded
    private Employer employer;

    @Embedded
    private WorkEffortDuration duration;
}
