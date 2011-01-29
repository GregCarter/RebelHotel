package edu.unlv.cs.rebelhotel.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import edu.unlv.cs.rebelhotel.domain.Semester;
import javax.validation.constraints.NotNull;

@RooJavaBean
@RooToString
@RooEntity
public class Term {

    @NotNull
    private Integer termYear;

    private Semester semester;
}
