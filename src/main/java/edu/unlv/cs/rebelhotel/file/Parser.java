package edu.unlv.cs.rebelhotel.file;

import edu.unlv.cs.rebelhotel.file.FileStudent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;
import org.springframework.web.multipart.MultipartFile;

public interface Parser {
	public Set<FileStudent> parse() throws IOException;
}
