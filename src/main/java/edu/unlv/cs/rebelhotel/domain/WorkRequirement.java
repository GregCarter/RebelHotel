package edu.unlv.cs.rebelhotel.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import edu.unlv.cs.rebelhotel.domain.Student;
import javax.persistence.ManyToOne;

@RooJavaBean
@RooToString
@RooEntity
public class WorkRequirement {

    @NotNull
    private String name;

    private Integer hours;

    @ManyToOne
    private Student student;
}
