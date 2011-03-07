package edu.unlv.cs.rebelhotel.file;

import edu.unlv.cs.rebelhotel.domain.Student;

public class StudentMapper {
	public StudentMapper() {
	}
	
	public Student findOrReplace(String studentId){
		Student student;
		student = Student.findStudentsByUserIdEquals(studentId).getSingleResult();
		if (student == null) {
			student = new Student();
		}
		return student;
	}
}
