package edu.unlv.cs.rebelhotel.domain;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import edu.unlv.cs.rebelhotel.domain.Student;
import javax.validation.constraints.NotNull;
import javax.persistence.ManyToOne;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

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
import edu.unlv.cs.rebelhotel.domain.CatalogRequirement;

import java.util.HashSet;
import javax.persistence.ManyToMany;

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
    private Set<CatalogRequirement> catalogRequirements = new HashSet<CatalogRequirement>();
    
    @PostPersist
    public void persistHours() {
    	DetachedCriteria dc = DetachedCriteria.forClass(Student.class, "iq")
    	.createAlias("workEffort", "we")
    	.setProjection(Projections.projectionList()
    			.add(Projections.sum("we.duration.hours")))
    	.add(Restrictions.eq("iq.id", student.getId()))
    	.add(Restrictions.eq("we.verification", Verification.ACCEPTED))
    	.add(Restrictions.ne("we.validation", Validation.FAILED_VALIDATION));
    	
    	Session session = ((Session) Student.entityManager().unwrap(Session.class)).getSessionFactory().openSession();
    	session.beginTransaction();
    	Criteria query = dc.getExecutableCriteria(session);
    	
    	Long totalHours = (Long) query.list().get(0);
    	totalHours = totalHours + duration.getHours();
    	
    	student.setTotalHours(totalHours);
    	session.close();
    }
    
    @PostUpdate
    public void updateHours() {
    	DetachedCriteria dc = DetachedCriteria.forClass(Student.class, "iq")
    	.createAlias("workEffort", "we")
    	.setProjection(Projections.projectionList()
    			.add(Projections.sum("we.duration.hours")))
    	.add(Restrictions.eq("iq.id", student.getId()))
    	.add(Restrictions.ne("we.id", getId()))
    	.add(Restrictions.eq("we.verification", Verification.ACCEPTED))
    	.add(Restrictions.ne("we.validation", Validation.FAILED_VALIDATION));
    	
    	Session session = ((Session) Student.entityManager().unwrap(Session.class)).getSessionFactory().openSession();
    	session.beginTransaction();
    	Criteria query = dc.getExecutableCriteria(session);
    	
    	Long totalHours = (Long) query.list().get(0);
    	totalHours = totalHours + duration.getHours();
    	
    	student.setTotalHours(totalHours);
    	session.close();
    }
    
    @PostRemove
    public void removeHours() {
    	DetachedCriteria dc = DetachedCriteria.forClass(Student.class, "iq")
    	.createAlias("workEffort", "we")
    	.setProjection(Projections.projectionList()
    			.add(Projections.sum("we.duration.hours")))
    	.add(Restrictions.eq("iq.id", student.getId()))
    	.add(Restrictions.ne("we.id", getId()))
    	.add(Restrictions.eq("we.verification", Verification.ACCEPTED))
    	.add(Restrictions.ne("we.validation", Validation.FAILED_VALIDATION));
    	
    	Session session = ((Session) Student.entityManager().unwrap(Session.class)).getSessionFactory().openSession();
    	session.beginTransaction();
    	Criteria query = dc.getExecutableCriteria(session);
    	
    	Long totalHours = (Long) query.list().get(0);
    	
    	student.setTotalHours(totalHours);
    	session.close();
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Position: ").append(getWorkPosition()).append("\n");
        sb.append("At: ").append(getEmployer().getName()).append("\n");
        sb.append("Duration: ").append(getDuration()).append("\n").append("\n");
        return sb.toString();
    }
}
