package edu.unlv.cs.rebelhotel.form;

import java.util.Date;

import edu.unlv.cs.rebelhotel.domain.Term;
import edu.unlv.cs.rebelhotel.domain.enums.Departments;

public class FormStudentQuery {
	private boolean useUserId;
	private String userId;
	
	private boolean useModified;
	private Date lastModifiedStart;
	private Date lastModifiedEnd;
	
	private boolean useCatalogTerm;
	private Term catalogTerm;
	
	private boolean useGradTerm;
	private Term gradTerm;
	
	private boolean useMilestone;
	private boolean hasMilestone;
	
	private boolean useMajor;
	private Departments department;
	
	public boolean getUseUserId() {
		return useUserId;
	}
	
	public void setUseUserId(boolean useUserId) {
		this.useUserId = useUserId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public boolean getUseModified() {
		return useModified;
	}
	
	public void setUseModified(boolean useModified) {
		this.useModified = useModified;
	}
	
	public Date getLastModifiedStart() {
		return lastModifiedStart;
	}
	
	public void setLastModifiedStart(Date lastModifiedStart) {
		this.lastModifiedStart = lastModifiedStart;
	}
	
	public Date getLastModifiedEnd() {
		return lastModifiedEnd;
	}
	
	public void setLastModifiedEnd(Date lastModifiedEnd) {
		this.lastModifiedEnd = lastModifiedEnd;
	}
	
	public boolean getUseCatalogTerm() {
		return useCatalogTerm;
	}
	
	public void setUseCatalogTerm(boolean useCatalogTerm) {
		this.useCatalogTerm = useCatalogTerm;
	}
	
	public Term getCatalogTerm() {
		return catalogTerm;
	}
	
	public void setCatalogTerm(Term admitTerm) {
		this.catalogTerm = admitTerm;
	}
	
	public boolean getUseGradTerm() {
		return useGradTerm;
	}
	
	public void setUseGradTerm(boolean useGradTerm) {
		this.useGradTerm = useGradTerm;
	}
	
	public Term getGradTerm() {
		return gradTerm;
	}
	
	public void setGradTerm(Term gradTerm) {
		this.gradTerm = gradTerm;
	}
	
	public boolean isStartDateAfterEndDate() {
    	return (lastModifiedStart.compareTo(lastModifiedEnd) > 0);
    }
	
	public boolean getHasMilestone() {
		return hasMilestone;
	}
	
	public void setHasMilestone(boolean hasMilestone) {
		this.hasMilestone = hasMilestone;
	}
	
	public boolean getUseMilestone() {
		return useMilestone;
	}
	
	public void setUseMilestone(boolean useMilestone) {
		this.useMilestone = useMilestone;
	}
	
	public boolean getUseMajor() {
		return useMajor;
	}
	
	public void setUseMajor(boolean useMajor) {
		this.useMajor = useMajor;
	}
	
	public Departments getDepartment() {
		return department;
	}
	
	public void setDepartment(Departments department) {
		this.department = department;
	}
}