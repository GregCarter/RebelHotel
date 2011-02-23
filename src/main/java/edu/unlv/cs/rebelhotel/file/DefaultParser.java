package edu.unlv.cs.rebelhotel.file;

import java.util.Hashtable;
import edu.unlv.cs.rebelhotel.file.Line;
import edu.unlv.cs.rebelhotel.file.FileStudent;
import java.util.Set;
import java.util.HashSet;

import org.springframework.web.multipart.MultipartFile;

public class DefaultParser implements Parser {
	private Hashtable<String,Set<Line>> entries = new Hashtable<String,Set<Line>>();
	private Set<FileStudent> fileStudents = new HashSet<FileStudent>();
	
	public DefaultParser(){
		// do some initialization...maybe something with the file?
	}
	
	public Set<FileStudent> parse() {
		// do some actual parsing
		// this is where you will have to create lines
		// then you take the lines and convert them to a FileStudent
		// somewhere around here, you extract the info
		boolean eof = false;
		Set<Line> tempLineSet;
		while (!eof) {
			// ideally, we want to grab the ID from the file, but
			// for now we will just make one
			String studentId = "studentId";
			// ideally, we want to use the other constructor,
			// but for now we will be lazy and not initialize any values
			Line line = new Line();
			
			if (entries.contains(studentId)) {
				tempLineSet = entries.get(studentId);
			} else {
				tempLineSet = new HashSet<Line>();
			}
			
			tempLineSet.add(line);
			entries.put(studentId,tempLineSet);
		}
		FileStudent fs = new FileStudent();
		fileStudents.addAll(fs.convert(entries));
		return this.fileStudents;
	}
}
