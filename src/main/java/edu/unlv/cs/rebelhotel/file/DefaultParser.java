package edu.unlv.cs.rebelhotel.file;

import edu.unlv.cs.rebelhotel.file.Line;
import edu.unlv.cs.rebelhotel.file.FileStudent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultParser implements Parser {
	
	public DefaultParser(){
	}
	
	/** 
	 * Parse receives a list of list of strings as the input parameter.
	 * (Imagine a list of lines, and each line is a list of strings (tokens))
	 * 
	 * @return Set<FileStudent>
	 */
	public Set<FileStudent> parse(List<List<String>> contents) {
		Map<String,Set<Line>> entries = new HashMap<String,Set<Line>>();
		Set<FileStudent> fileStudents = new HashSet<FileStudent>();
		
		Line newline = new Line();
		
		for (List<String> tokens : contents) {
			newline = newline.convert(tokens);
			String studentId = newline.getStudentId();
			Set<Line> tempLineSet;
			if (entries.containsKey(studentId)) {
				tempLineSet = entries.get(studentId);
			} else {
				tempLineSet = new HashSet<Line>();
			}
			tempLineSet.add(newline);
			entries.put(studentId,tempLineSet);
		}
		FileStudent fs = new FileStudent();
		fileStudents.addAll(fs.convert(entries));
		return fileStudents;
	}
}
