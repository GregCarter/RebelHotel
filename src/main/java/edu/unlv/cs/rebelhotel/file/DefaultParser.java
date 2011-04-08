package edu.unlv.cs.rebelhotel.file;

import edu.unlv.cs.rebelhotel.file.FileStudent;
import edu.unlv.cs.rebelhotel.file.Line;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import org.springframework.stereotype.Component;


@Component
public class DefaultParser implements Parser {

	/** 
	 * Parse receives a list of list of strings as the input parameter.
	 * (Imagine a list of lines, and each line is a list of strings (tokens))
	 * 
	 * @return Set<FileStudent>
	 */
	public Set<FileStudent> parse(List<List<String>> contents) {
		Map<String,List<Line>> entries = new HashMap<String,List<Line>>();
		Set<FileStudent> fileStudents = new HashSet<FileStudent>();

		for (List<String> tokens : contents) {
			Line newline = new Line(tokens);
			if (!newline.getMajors().isEmpty()) {
				String studentId = newline.getStudentId();
				List<Line> tempLineSet;
				if (entries.containsKey(studentId)) {
					tempLineSet = entries.get(studentId);
				} else {
					tempLineSet = new ArrayList<Line>();
				}
				tempLineSet.add(newline);
				entries.put(studentId,tempLineSet);
			}
		}
		FileStudent fs = new FileStudent();
		fileStudents.addAll(fs.convert(entries.values()));
		return fileStudents;
	}
}
