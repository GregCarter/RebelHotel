package edu.unlv.cs.rebelhotel.service;


import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import edu.unlv.cs.rebelhotel.domain.Major;
import edu.unlv.cs.rebelhotel.domain.WorkRequirement;
import edu.unlv.cs.rebelhotel.domain.WorkTemplate;


@Component
public class WorkRequirementService {
	
	public Set<Major> updateStudentInformation(Set<Major> current_majors, Set<Major> majors){
		if (current_majors.isEmpty()) { // this means the student is new
			for (Major major : majors) {
				addNewMajor(current_majors,major);
			}
		} else {
			for (Major current : current_majors) {
				for (Major major : majors) {
					if (!current.getDegree().equals(major.getDegree())){
						addNewMajor(current_majors,major);
					}
				}
			}
		}
		return current_majors;
	}
	
	private Set<Major> addNewMajor(Set<Major> current_majors, Major major) {
		int total_hours_needed = 500;
		
		List<WorkTemplate> workTemplates = WorkTemplate.findWorkTemplatesByDegreeAndTermEquals(major.getDegree(),major.getCatalogTerm()).getResultList();
		if (workTemplates.isEmpty()) {
			//new throw InstanceDoesNotExist("This work requirement template does not exist: Degree = " + major.getDegree() + " Term: " + major.getCatalogTerm());
			WorkTemplate wt = new WorkTemplate();
			wt.setDegree(major.getDegree());
			wt.setName("");
			wt.setTerm(major.getCatalogTerm());
			wt.setTotalHoursNeeded(total_hours_needed);
			wt.persist();
			workTemplates.add(wt);
		}
		
		for (WorkTemplate workTemplate : workTemplates) {
			WorkRequirement workRequirement = WorkRequirement.fromWorkTemplate(workTemplate, major);
			workRequirement.persist();
			major.getWorkRequirements().add(workRequirement);
		}
		current_majors.add(major);
		major.persist();
		return current_majors;
	}
}