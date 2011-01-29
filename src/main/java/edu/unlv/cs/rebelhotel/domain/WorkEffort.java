package edu.unlv.cs.rebelhotel.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import edu.unlv.cs.rebelhotel.domain.Student;
import javax.validation.constraints.NotNull;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import edu.unlv.cs.rebelhotel.domain.Verification;
import edu.unlv.cs.rebelhotel.domain.Employer;
import edu.unlv.cs.rebelhotel.domain.Supervisor;
import javax.persistence.Embedded;

@RooJavaBean
@RooToString
@RooEntity
public class WorkEffort {

    @NotNull
    @ManyToOne
    private Student Student;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date endDate;

    private Integer hours;

    private String workPosition;

    private Verification verification;

    @NotNull
    @ManyToOne
    private Employer employer;

    private String comment;
}
