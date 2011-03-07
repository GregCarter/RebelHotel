package edu.unlv.cs.rebelhotel.file;

import edu.unlv.cs.rebelhotel.file.FileStudent;

import java.util.List;
import java.util.Set;

public interface Parser {
	public Set<FileStudent> parse(List<List<String>> contents);
}
