package edu.unlv.cs.rebelhotel.service;


import org.springframework.stereotype.Component;


import java.util.HashSet;
//import java.util.List;
import java.util.Set;

import edu.unlv.cs.rebelhotel.domain.Major;
import edu.unlv.cs.rebelhotel.domain.WorkRequirement;
import edu.unlv.cs.rebelhotel.domain.WorkTemplate;


@Component
public class WorkRequirementService {
	
	public Set<Major> updateStudentInformation(Set<Major> current_majors, Set<Major> majors){
		if (0 == current_majors.size()) { // this means the student is new
			for (Major each : majors) {
				WorkRequirement workRequirement = WorkRequirement.fromWorkTemplate(WorkTemplate.findWorkTemplatesByNameEquals("Gaming").getSingleResult(), each);
				workRequirement.persist();
				each.getWorkRequirements().add(workRequirement);
				current_majors.add(each);
				each.persist();
			}
		} else {
			for (Major current : current_majors) {
				for (Major each : majors) {
					if (!current.getDepartment().equals(each.getDepartment())){
						WorkRequirement workRequirement = WorkRequirement.fromWorkTemplate(WorkTemplate.findWorkTemplatesByNameEquals("Gaming").getSingleResult(), each);
						workRequirement.persist();
						each.getWorkRequirements().add(workRequirement);
						current_majors.add(each);
						each.persist();
					}
				}
			}
		}
		return current_majors;
	}

}