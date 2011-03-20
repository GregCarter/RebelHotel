package edu.unlv.cs.rebelhotel.service;

import java.util.HashSet;
//import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import edu.unlv.cs.rebelhotel.domain.Major;
import edu.unlv.cs.rebelhotel.domain.Term;
import edu.unlv.cs.rebelhotel.domain.WorkRequirement;
import edu.unlv.cs.rebelhotel.domain.WorkTemplate;
import edu.unlv.cs.rebelhotel.domain.enums.Departments;
import edu.unlv.cs.rebelhotel.file.enums.FileDepartments;

@Component
public class WorkRequirementService {
	
	public Set<Major> updateStudentInformation(Set<Major> current_majors, Set<String> majors, Term requirementTerm){
		//List<WorkTemplate> workTemplates = WorkTemplate.findAllWorkTemplates();
		Set<WorkRequirement> workRequirements = new HashSet<WorkRequirement>();
		
		/* now get the work requirements from those majors. 
		 * this implementation might change later if Sam decides to make the changes he anticipated.
		 */
		
		// Do this just in case the student is a preexisting one
		for (Major each : current_majors) {
			workRequirements.addAll(each.getWorkRequirements());
		}

		// Create any new majors
		Major m;
		Set<Major> newMajors = new HashSet<Major>();
		for (WorkRequirement each : workRequirements) {
			m = each.getMajor();
			for (String majorName : majors) {
				if (!majorName.equals(m.getDepartment())){
					Major new_major = new Major();
					Set<WorkRequirement> wr = new HashSet<WorkRequirement>();
					
					// change this later: Go into the application
					// and manually change the work template names to match
					// the department names. Afterwards, change the
					// string field in the worktemplate and workrequirements
					// to a department enum...
					WorkRequirement workRequirement = WorkRequirement.fromWorkTemplate(WorkTemplate.findWorkTemplatesByNameEquals("Gaming").getSingleResult(), new_major); 
					workRequirement.persist();
					
					wr.add(workRequirement);
					new_major.setWorkRequirements(wr);
					new_major.setCatalogTerm(requirementTerm);
					new_major.setDepartment(departmentMapper(majorName));
					new_major.setReachedMilestone(false);
					new_major.persist();		
					newMajors.add(new_major);
				}
			}
		}
		current_majors.addAll(newMajors);
		return current_majors;
	}
	
	private Departments departmentMapper(String major) {
		if (major.equals(FileDepartments.CAMBSCM)) {
			return Departments.CULINARY_ARTS_MANAGEMENT;
		} else if (major.equals(FileDepartments.CAMPRE)) {
			return Departments.CULINARY_ARTS_MANAGEMENT_PRE;
		} else if (major.equals(FileDepartments.CBEVBSCM)) {
			return Departments.CULINARY_ARTS_BEVERAGE_MANAGEMENT;
		} else if (major.equals(FileDepartments.CBEVPRBSCM)) {
			return Departments.CULINARY_ARTS_BEVERAGE_MANAGEMENT_PRE;
		} else if (major.equals(FileDepartments.FDMBSHA)) {
			return Departments.FOOD_SERVICE_MANAGEMENT;
		} else if (major.equals(FileDepartments.FDMPRE)) {
			return Departments.FOOD_SERVICE_MANAGEMENT_PRE;
		} else if (major.equals(FileDepartments.GAMBSGM)) {
			return Departments.GAMING_MANAGEMENT;
		} else if (major.equals(FileDepartments.GAMPRE)) {
			return Departments.GAMING_MANAGEMENT_PRE;
		} else if (major.equals(FileDepartments.HBEVBSHA)) {
			return Departments.HOTEL_ADMINISTRATION_BEVERAGE_MANAGEMENT;
		} else if (major.equals(FileDepartments.HBEVPRBSHA)) {
			return Departments.HOTEL_ADMINISTRATION_BEVERAGE_MANAGEMENT_PRE;
		} else if (major.equals(FileDepartments.HOSSINBSMS)) {
			return Departments.HOSPITALITY_MANAGEMENT;
		} else if (major.equals(FileDepartments.LRMBSHA)) {
			return Departments.LODGING_AND_RESORT_MANAGEMENT;
		} else if (major.equals(FileDepartments.LRMPRE)) {
			return Departments.LODGING_AND_RESORT_MANAGEMENT_PRE;
		} else if (major.equals(FileDepartments.MEMBSHA)) {
			return Departments.MEETINGS_AND_EVENTS_MANAGEMENT;
		} else if (major.equals(FileDepartments.MEMPRE)) {
			return Departments.MEETINGS_AND_EVENTS_MANAGEMENT_PRE;
		} else {
			throw new IllegalArgumentException("Invalid Major: " + major);
		}
	}
}