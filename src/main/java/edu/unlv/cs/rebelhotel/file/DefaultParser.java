package edu.unlv.cs.rebelhotel.file;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import edu.unlv.cs.rebelhotel.file.Line;
import edu.unlv.cs.rebelhotel.file.FileStudent;
import java.util.Set;
import java.util.HashSet;

import org.springframework.web.multipart.MultipartFile;

import au.com.bytecode.opencsv.CSVReader;

public class DefaultParser implements Parser {
	private Hashtable<String,Set<Line>> entries = new Hashtable<String,Set<Line>>();
	private Set<FileStudent> fileStudents = new HashSet<FileStudent>();
	
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
	public Set<FileStudent> parse() throws IOException {
		CSVReader reader = new CSVReader(new FileReader("students.txt"));
		String [] nextLine;
		while ((nextLine = reader.readNext()) != null) {
	        // nextLine[] is an array of values from the line
			Set<Line> tempLineSet;
			String studentId = "1000178965";
			// ideally, we want to use the other constructor,
			// but for now we will be lazy and not initialize any values
			// for future reference, all you do is nextLine[x],nextLine[x+1],...
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
