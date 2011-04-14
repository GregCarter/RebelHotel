package edu.unlv.cs.rebelhotel.file;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import edu.unlv.cs.rebelhotel.domain.Student;
import edu.unlv.cs.rebelhotel.domain.UserAccount;
import edu.unlv.cs.rebelhotel.file.RandomPasswordGenerator;

@Component
public class StudentMapper {
	public Student findOrReplace(FileStudent fileStudent){
		Student student = existingOrNewStudent(fileStudent);
		student.setUserId(fileStudent.getStudentId());
		student.setFirstName(fileStudent.getFirstName());
		student.setLastName(fileStudent.getLastName());
		student.setMiddleName(fileStudent.getMiddleName());
		student.setEmail(fileStudent.getEmail());
		student.setGradTerm(fileStudent.getGradTerm());
		student.setAdmitTerm(fileStudent.getAdmitTerm());
		student.updateMajors(fileStudent.getMajors());
		student.setCodeOfConductSigned(false);

		UserAccount studentAccount = existingOrNewAccount(fileStudent);
		student.setUserAccount(studentAccount);
		
		return student;
	}

	private Student existingOrNewStudent(FileStudent fileStudent) {
		Student student;
		try {
			student = Student.findStudentsByUserIdEquals(fileStudent.getStudentId()).getSingleResult();
			return student;
		} catch(EmptyResultDataAccessException e) {
			student = new Student();
		}
		return student;
	}
	
	private UserAccount existingOrNewAccount(FileStudent fileStudent) {
		UserAccount studentAccount;
		try {
			studentAccount = UserAccount.findUserAccountsByUserId(fileStudent.getStudentId()).getSingleResult();
			return studentAccount;
		} catch(EmptyResultDataAccessException e) {
			RandomPasswordGenerator rpg = new RandomPasswordGenerator();
			//studentAccount = new UserAccount(fileStudent,rpg.generateRandomPassword());
			studentAccount = UserAccount.fromFileStudent(fileStudent, rpg.generateRandomPassword());
			studentAccount.persist();
		}
		return studentAccount;
	}
}
