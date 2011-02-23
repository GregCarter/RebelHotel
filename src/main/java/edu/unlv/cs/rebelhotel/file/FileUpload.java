package edu.unlv.cs.rebelhotel.file;

import org.springframework.web.multipart.MultipartFile;
import edu.unlv.cs.rebelhotel.file.DefaultParser;
import java.util.Set;
import java.util.HashSet;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class FileUpload {
	private DefaultParser parser;
	
	public FileUpload() {
	}
	
	public Set<FileStudent> parse() {
		Set<FileStudent> fileStudents = new HashSet<FileStudent>();
		parser.parse();
		return fileStudents;
	}
}