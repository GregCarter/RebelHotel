package edu.unlv.cs.rebelhotel.file;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public interface Lexer {
	public List<List<String>> tokenize(Reader reader) throws IOException;
}
