package edu.unlv.cs.rebelhotel.domain;

import java.util.HashSet;
import java.util.Set;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
/*import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import edu.unlv.cs.rebelhotel.domain.Student;
import javax.persistence.Embeddable;
*/
import edu.unlv.cs.rebelhotel.domain.enums.Validation;
import edu.unlv.cs.rebelhotel.domain.enums.Verification;
import edu.unlv.cs.rebelhotel.domain.WorkRequirement;
import javax.persistence.OneToOne;

@RooJavaBean
@RooToString
@RooEntity

public class ViewProgress {
	
	// You need the student here because a milestone is essentially a work requirement.
	// Once they have fulfilled the work requirement, they have fulfilled a milestone, right?
	// Well, assuming a milestone = a work requirement, then all we need is the student and their
	// work requirements.
	
	@OneToOne
	private WorkRequirement workRequirement;
	private Integer approvedHours;
	
	public Integer computeApprovedHours(WorkRequirement wr) {
		Integer approvedHours = 0;
		Set<WorkEffort> workEfforts = new HashSet<WorkEffort>();
		workEfforts.addAll(workRequirement.getWorkEffort());
		for (WorkEffort we : workEfforts){
			// I am not so sure about this if statement, but my understanding is that
			// it is possible that a student will never undergo validation.
			// Therefore, the only times you do not count the hours is when
			// they either failed validation or are pending for validation.
			if (we.getVerification().equals(Verification.ACCEPTED))
				if (!we.getValidation().equals(Validation.FAILED_VALIDATION))
					if (!we.getValidation().equals(Validation.PENDING))
						approvedHours += we.getDuration().getHours();
    	}
		return approvedHours;
	}
	
	public Integer getRemainingHours() {
		return (workRequirement.getHours() - approvedHours);
		// return ViewProgress.getWorkRequirement.getHours() - ViewProgress.getApprovedHours();
	}
	
	public Integer getRemainingHours(WorkRequirement wr, Integer approvedHours) {
		return (wr.getHours() - approvedHours);
	}

	/*public void setMajor1ApproveHours() {
    	if (!student.getMajor1().equals(null)) {
    		Set<WorkRequirement> workRequirements = new HashSet<WorkRequirement>();
    		workRequirements.addAll(student.getWorkRequirement());
    		for (WorkRequirement wr : workRequirements){
    			if (student.getMajor1().equals(wr.getName())){
    				Set<WorkEffort> workEfforts = new HashSet<WorkEffort>();
    				workEfforts.addAll(student.getWorkEffort());
    				for (WorkEffort we : workEfforts){
    					major1ApprovedHours += we.getDuration().getHours();
    				}
    			}
    		}
    	}
    }
    
    public void setMajor2ApprovedHours(){
    	if (!student.getMajor2().equals(null)) {
    		Set<WorkRequirement> workRequirements = new HashSet<WorkRequirement>();
    		workRequirements.addAll(student.getWorkRequirement());
    		for (WorkRequirement wr : workRequirements){
    			if (student.getMajor2().equals(wr.getName())){
    				Set<WorkEffort> workEfforts = new HashSet<WorkEffort>();
    				workEfforts.addAll(student.getWorkEffort());
    				for (WorkEffort we : workEfforts){
    					major2ApprovedHours += we.getDuration().getHours();
    				}
    			}
    		}
    	}
    }
   
    public void setMajor1RemainingHours() {
      	if (!student.getMajor1().equals(null)) {
    		Set<WorkRequirement> workRequirements = new HashSet<WorkRequirement>();
    		workRequirements.addAll(student.getWorkRequirement());
    		for (WorkRequirement wr : workRequirements){
    			if (student.getMajor1().equals(wr.getName())){
    				major1RemainingHours = wr.getHours() - major1ApprovedHours;
    			}
    		}
    	}
    }
    
    public void setMajor2RemainingHours() {
      	if (!student.getMajor2().equals(null)) {
    		Set<WorkRequirement> workRequirements = new HashSet<WorkRequirement>();
    		workRequirements.addAll(student.getWorkRequirement());
    		for (WorkRequirement wr : workRequirements){
    			if (student.getMajor2().equals(wr.getName())){
    				major2RemainingHours = wr.getHours() - major2ApprovedHours;
    			}
    		}
    	}
    }
	*/	
}