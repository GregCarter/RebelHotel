package edu.unlv.cs.rebelhotel.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.unlv.cs.rebelhotel.domain.Student;
import edu.unlv.cs.rebelhotel.service.WorkRequirementService;

@Component
public class StudentMapper {
	WorkRequirementService workRequirementService;
	
	@Autowired
	public StudentMapper(WorkRequirementService workRequirementService) {
		this.workRequirementService = workRequirementService;
	}
	
	public Student findOrReplace(FileStudent fileStudent){
		Student student;
		student = Student.findStudentsByUserIdEquals(fileStudent.getStudentId()).getSingleResult();
		if (student == null) {
			student = new Student();
		}
		return student;
	}
}
