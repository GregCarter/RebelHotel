package edu.unlv.cs.rebelhotel.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RooJavaBean
@RooToString
@RooEntity
public class ViewProgress {

    private Integer NSHE;

    @NotNull
    @Size(min = 2)
    private String first;

    @NotNull
    @Size(min = 2)
    private String last;

    @NotNull
    @Size(min = 2)
    private String middle;

    @NotNull
    @Size(min = 2)
    private String email;

    @NotNull
    @Size(min = 2)
    private String major1;

    @NotNull
    @Size(min = 2)
    private String major2;

    @NotNull
    @Size(min = 2)
    private String admitTerm;

    @NotNull
    @Size(min = 2)
    private String expectedGrad;

    @NotNull
    @Size(min = 2)
    private String major1Milestone;

    @NotNull
    @Size(min = 2)
    private String major2Milestone;

    private Integer major1_approved_hrs;

    private Integer major1_remaining_hrs;

    private Integer major2_approved_hrs;

    private Integer major2_remaining_hrs;

    private Integer total_hrs_approved;

    private Integer total_hrs_remaining;

    @NotNull
    @Size(min = 2)
    private String general_requirement;
}
