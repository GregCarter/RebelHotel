package edu.unlv.cs.rebelhotel.file;

import edu.unlv.cs.rebelhotel.file.DefaultParser;

import java.io.IOException;
import java.util.Set;
import java.util.HashSet;

public class FileUpload {
	private DefaultParser parser;
	
	public FileUpload() {
	}
	
	public Set<FileStudent> parse() throws IOException {
		Set<FileStudent> fileStudents = new HashSet<FileStudent>();
		fileStudents = parser.parse();
		return fileStudents;
	}
}