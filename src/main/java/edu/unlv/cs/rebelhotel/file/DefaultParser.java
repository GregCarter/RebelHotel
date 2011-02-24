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
		// do some initialization...maybe something with the file?
	}
	
	public Set<FileStudent> parse() throws IOException {
		// do some actual parsing
		// this is where you will have to create lines
		// then you take the lines and convert them to a FileStudent
		// somewhere around here, you extract the info
		
		// open the reader
		// go through each line
		CSVReader reader = new CSVReader(new FileReader("/data/students.csv"));
		String [] nextLine;
		while ((nextLine = reader.readNext()) != null) {
	        // nextLine[] is an array of values from the line
			Set<Line> tempLineSet;
			// ideally, we want to grab the ID from the file, but
			// for now we will just make one
			String studentId = "studentId";	
			// ideally, we want to use the other constructor,
			// but for now we will be lazy and not initialize any values
			Line line = new Line(nextLine[0],nextLine[1],nextLine[2],
								nextLine[3],nextLine[4],nextLine[5],nextLine[6],
								nextLine[7],nextLine[8],nextLine[9]);
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
