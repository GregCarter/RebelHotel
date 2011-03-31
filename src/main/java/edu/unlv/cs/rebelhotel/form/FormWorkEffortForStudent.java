package edu.unlv.cs.rebelhotel.form;

import java.util.Set;

import edu.unlv.cs.rebelhotel.domain.Major;
import edu.unlv.cs.rebelhotel.domain.WorkEffort;

public class FormWorkEffortForStudent {
	private WorkEffort workEffort;
	private Set<Major> majors;
	
	public WorkEffort getWorkEffort() {
		return workEffort;
	}
	
	public void setWorkEffort(WorkEffort workEffort) {
		this.workEffort = workEffort;
	}
	
	public Set<Major> getMajors() {
		return majors;
	}
	
	public void setMajors(Set<Major> majors) {
		this.majors = majors;
	}
}