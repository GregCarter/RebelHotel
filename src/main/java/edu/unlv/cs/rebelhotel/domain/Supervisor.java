package edu.unlv.cs.rebelhotel.domain;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.persistence.Embeddable;

@RooJavaBean
@RooToString
@Embeddable
public class Supervisor {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getFirstName()+" "+getLastName());
        return sb.toString();
    }
}
