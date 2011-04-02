package edu.unlv.cs.rebelhotel.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import edu.unlv.cs.rebelhotel.domain.Student;
import java.util.List;
import java.util.Set;
import javax.persistence.TypedQuery;
import edu.unlv.cs.rebelhotel.domain.Major;
import edu.unlv.cs.rebelhotel.domain.Term;
import edu.unlv.cs.rebelhotel.domain.UserAccount;
import edu.unlv.cs.rebelhotel.domain.enums.UserGroup;
import edu.unlv.cs.rebelhotel.service.WorkRequirementService;
import edu.unlv.cs.rebelhotel.file.RandomPasswordGenerator;

@Component
public class StudentMapper {
	private WorkRequirementService workRequirementService;
	
	@Autowired
	public StudentMapper(WorkRequirementService workRequirementService) {
		this.workRequirementService = workRequirementService;
	}

	public Student findOrReplace(FileStudent fileStudent){
		Student student = preexistingOrNewStudent(fileStudent);
		student.setUserId(fileStudent.getStudentId());
		student.setFirstName(fileStudent.getFirstName());
		student.setLastName(fileStudent.getLastName());
		student.setMiddleName(fileStudent.getMiddleName());
		student.setEmail(fileStudent.getEmail());
		student.setGradTerm(fileStudent.getGradTerm());
		student.setAdmitTerm(fileStudent.getAdmitTerm());

		/* if both major columns are populated, and there is only one requirement term,
		 * then can we assume that it is the same for both majors? I suppose...but what if
		 * they take up another major three years after declaring the first one?
		 */
		Set<Major> majors = workRequirementService.updateStudentInformation(student.getMajors(),fileStudent.getMajors());
		student.setMajors(majors);
		student.setCodeOfConductSigned(false);

		UserAccount student_account = preexistingOrNewAccount(fileStudent);
		student.setUserAccount(student_account);
		
		return student;
	}

	private Student preexistingOrNewStudent(FileStudent fileStudent) {
		Student student;
		try {
			student = Student.findStudentsByUserIdEquals(fileStudent.getStudentId()).getSingleResult();
			return student;
		} catch(EmptyResultDataAccessException e) {
			student = new Student();
		}
		return student;
	}
	
	private UserAccount preexistingOrNewAccount(FileStudent fileStudent) {
		UserAccount student_account;
		try {
			student_account = UserAccount.findUserAccountsByUserId(fileStudent.getStudentId()).getSingleResult();
			return student_account;
		} catch(EmptyResultDataAccessException e) {
			RandomPasswordGenerator rpg = new RandomPasswordGenerator();
			student_account = new UserAccount();
			student_account.setUserId(fileStudent.getStudentId());
			student_account.setPassword(rpg.generateRandomPassword());
			student_account.setUserGroup(UserGroup.ROLE_USER);
			student_account.setEmail(fileStudent.getEmail());
			student_account.persist();
		}
		return student_account;
	}
}
