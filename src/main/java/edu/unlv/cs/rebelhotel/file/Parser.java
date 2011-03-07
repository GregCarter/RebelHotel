package edu.unlv.cs.rebelhotel.file;

import java.util.List;
import java.util.Set;

public interface Parser {
	public Set<FileStudent> parse(List<List<String>> contents);
}
