package edu.unlv.cs.rebelhotel.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import edu.unlv.cs.rebelhotel.domain.Term;
import edu.unlv.cs.rebelhotel.domain.WorkEffort;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findStudentsByFirstNameEquals", "findStudentsByFirstNameLike", "findStudentsByUserAccount", "findStudentsByUserIdEquals" })
public class Student {

    @NotNull
    @Column(unique = true)
    private String userId;

    @NotNull
    @Size(min = 2)
    private String email;

    @NotNull
    @Size(min = 2)
    private String firstName;

    private String middleName;

    private String lastName;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Major> majors = new HashSet<Major>();

    @ManyToOne(cascade= { CascadeType.PERSIST, CascadeType.MERGE } )
    private Term admitTerm;

    @ManyToOne(cascade= { CascadeType.PERSIST, CascadeType.MERGE } )
    private Term gradTerm;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<WorkEffort> workEffort = new HashSet<WorkEffort>();

    private Boolean codeOfConductSigned;
    
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date lastModified;

    @OneToOne(optional = false, cascade= { CascadeType.PERSIST, CascadeType.MERGE } )
    private UserAccount userAccount;
    
    @PreUpdate
    @PrePersist
    public void onUpdate() {
    	lastModified = new Date();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(" + userId + ")");
        sb.append(" " + firstName);
        if (lastName != null) {
            sb.append(" " + lastName);
        }
        return sb.toString();
    }

    public void addWorkEffort(WorkEffort we) {
        workEffort.add(we);
    }
    
    public String getName() {
    	String name = firstName;
    	if (lastName != null) {
    		name += " " + lastName;
    	}
    	return name;
    }
}
