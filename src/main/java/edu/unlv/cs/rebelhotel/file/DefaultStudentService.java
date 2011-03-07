package edu.unlv.cs.rebelhotel.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import edu.unlv.cs.rebelhotel.file.FileStudent;
import edu.unlv.cs.rebelhotel.file.StudentMapper;
import edu.unlv.cs.rebelhotel.domain.Student;


// As with this class...
// anything that is a singleton must be thread safe, because there will be
// multiple threads accessing it simultaneously
// - fields should be defined "final"


@Service
public class DefaultStudentService implements StudentService{
    private static final Logger LOG = LoggerFactory.getLogger(DefaultStudentService.class);
	private Parser parser;
	private Lexer lexer;
	
	@Autowired
	public DefaultStudentService(Parser parser, Lexer lexer){
		this.parser = parser;
		this.lexer = lexer;
	}
	
	@Async
	public void upload(File file){
		List<List<String>> contents = Collections.emptyList();
		try {
			contents = lexer.tokenize(new FileReader(file));
		} catch (FileNotFoundException e) {
			LOG.error("Could not upload student file", e);
		}
		Set<FileStudent> fileStudents = parser.parse(contents);
		
		
		for (FileStudent each : fileStudents) {
			findOrReplace(each);
		}
	}
	
	public void findOrReplace(FileStudent fileStudent) {
		StudentMapper sm = new StudentMapper();
		Student student = sm.findOrReplace(fileStudent.getStudentId());
		/*student.setFirstName(fileStudent.getFirstName());
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
		student.setGradTerm(fileStudent.getGradTerm());*/
		student.persist();
	}
}
