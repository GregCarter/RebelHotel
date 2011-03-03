package edu.unlv.cs.rebelhotel.file;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import edu.unlv.cs.rebelhotel.file.Line;
import edu.unlv.cs.rebelhotel.file.FileStudent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import au.com.bytecode.opencsv.CSVReader;

@Component
public class DefaultParser implements Parser {
	private Map<String,Set<Line>> entries = new HashMap<String,Set<Line>>();
	private Set<FileStudent> fileStudents = new HashSet<FileStudent>();
	
	@Autowired
	public DefaultParser(){
	}
	
	/** 
	 * Implements opencsv to parse a csv file. This is where we do not
	 * create any Lines for "recreation" majors
	 * 
	 * NOTE: before you begin parsing the document into lines,
	 * remember it is likely that the first line will be a bunch
	 * of headers (names of the columns), so it would be best to 
	 * skip that line (deletion?) before jumping into this for loop...
	 * 
	 * @return Set<FileStudent> a set of FileStudents created after parsing a document into Line(s)
	 */
	public Set<FileStudent> parse(List<List<String>> contents) {
		for (Iterator<List<String>> line = contents.iterator(); line.hasNext();) {
			for (Iterator<String> elements = ((List<String>) line).iterator(); elements.hasNext();) {
				  // nextLine[] is an array of values from the line
				Set<Line> tempLineSet;
				String studentId = null;
				Line line = new Line();
				if (entries.containsKey(studentId)) {
					tempLineSet = entries.get(studentId);
				} else {
					tempLineSet = new HashSet<Line>();
				}
				tempLineSet.add(line);
				entries.put(studentId,tempLineSet);
			}
		}
		FileStudent fs = new FileStudent();
		fileStudents.addAll(fs.convert(entries));
		return this.fileStudents;
	}
}
