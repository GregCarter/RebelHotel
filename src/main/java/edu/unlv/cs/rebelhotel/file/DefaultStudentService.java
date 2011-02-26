package edu.unlv.cs.rebelhotel.file;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import edu.unlv.cs.rebelhotel.file.FileStudent;
import edu.unlv.cs.rebelhotel.file.FileUpload;
import edu.unlv.cs.rebelhotel.file.StudentMapper;
import edu.unlv.cs.rebelhotel.domain.Major;
import edu.unlv.cs.rebelhotel.domain.Student;
import edu.unlv.cs.rebelhotel.domain.Term;
import edu.unlv.cs.rebelhotel.domain.enums.Departments;
import edu.unlv.cs.rebelhotel.domain.enums.Semester;

@Service
public class DefaultStudentService implements StudentService{
	private FileUpload upload;
	private Set<FileStudent> fileStudents = new HashSet<FileStudent>();
	private MultipartFile file;
	
	public DefaultStudentService(){
	}
	
	public DefaultStudentService(MultipartFile file) {
		this.file = file;
	}

	@Async
	public void upload() throws IllegalStateException, IOException{
		file.transferTo(new File("/data/students.csv"));
		fileStudents.addAll(upload.parse());
		
		for (FileStudent each : fileStudents) {
			findOrReplace(each);
		}
	}
	
	public void findOrReplace(FileStudent fileStudent) {
		StudentMapper sm = new StudentMapper();
		Student student = sm.findOrReplace(fileStudent.getStudentId());
		student.setFirstName(fileStudent.getFirstName());
		student.setMiddleName(fileStudent.getMiddleName());
		student.setLastName(fileStudent.getLastName());
		student.setUserId(fileStudent.getStudentId());
		student.setEmail(fileStudent.getEmail());
		
		for (String each : fileStudent.getMajors()) {
			Major major = new Major();
			if (each == "FOOD_AND_BEVERAGE"){
				major.setDepartment(Departments.FOOD_AND_BEVERAGE);
			} else if (each == "HOTEL_MANAGEMENT") {
				major.setDepartment(Departments.HOTEL_MANAGEMENT);
			} else if (each == "TOURISM_AND_CONVENTION") {
				major.setDepartment(Departments.TOURISM_AND_CONVENTION);
			}
			major.setReachedMilestone(false);
			major.setCatalogTerm(null); // probably create this based today's date
			major.setWorkRequirements(null);
		}
		student.setAdmitTerm(fileStudent.getAdmitTerm());
		student.setGradTerm(fileStudent.getGradTerm());
		student.persist();
	}
}
