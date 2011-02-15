package edu.unlv.cs.rebelhotel.form;

import edu.unlv.cs.rebelhotel.domain.WorkTemplate;

// A form backing class for use in the work requirement creation form (which lists work templates)
public class FormWorkRequirement {
	private WorkTemplate workTemplate;
	
	public WorkTemplate getWorkTemplate() {
		return workTemplate;
	}
	
	public void setWorkTemplate(WorkTemplate workTemplate) {
		this.workTemplate = workTemplate;
	}
}