package edu.unlv.cs.rebelhotel.file;

import java.io.IOException;

import edu.unlv.cs.rebelhotel.file.FileStudent;
import edu.unlv.cs.rebelhotel.domain.Student;

public interface StudentService {
	public void upload() throws IllegalStateException, IOException;
	public void findOrReplace(FileStudent fileStudent);
}
