package edu.unlv.cs.rebelhotel.file;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import edu.unlv.cs.rebelhotel.file.FileStudent;
import edu.unlv.cs.rebelhotel.file.FileUpload;
import edu.unlv.cs.rebelhotel.file.StudentMapper;
import edu.unlv.cs.rebelhotel.domain.Student;
import edu.unlv.cs.rebelhotel.domain.Term;
import edu.unlv.cs.rebelhotel.domain.enums.Semester;

@Async
public class DefaultStudentService implements StudentService{
	private FileUpload upload;
	private Set<FileStudent> fileStudents = new HashSet<FileStudent>();
	private MultipartFile file;
	
	public DefaultStudentService(){
		
	}
	
	public DefaultStudentService(MultipartFile file) {
		this.file = file;
	}

	public void upload(){
		/*File file;
		MultipartFile mf;
		mf.transferTo(file);*/
		fileStudents.addAll(upload.parse());
	}
	
	public Student findOrReplace(FileStudent fileStudent) {
		StudentMapper sm = new StudentMapper();
		Student student = sm.findOrReplace(fileStudent.getStudentId());
		student.setFirstName(fileStudent.getFirstName());
		student.setMiddleName(fileStudent.getMiddleName());
		student.setLastName(fileStudent.getLastName());
		student.setUserId(fileStudent.getStudentId());
		student.setEmail(fileStudent.getEmail());
		
		//for (String each : fileStudent.getMajor())
		int temp = 1; // number of majors, which cannot exceed 2
		Iterator<String> iter = fileStudent.getMajor().iterator();
		while (iter.hasNext() && temp < 3){
			if (temp == 1) {
				student.setMajor1(iter.next().toString());
			}
			else if (temp == 2) {
				student.setMajor2(iter.next().toString());
			}
			temp++;
		}
		
		student.setAdmitTerm(fileStudent.getAdmitTerm());
		student.setGradTerm(fileStudent.getGradTerm());
		return student;
	}
}
