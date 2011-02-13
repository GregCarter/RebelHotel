package edu.unlv.cs.rebelhotel.domain;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.persistence.Embeddable;

@RooJavaBean
@RooToString
@Embeddable
public class Employer {

    private String location;

    private String name;
}
