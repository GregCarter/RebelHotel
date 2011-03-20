package edu.unlv.cs.rebelhotel.file;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import au.com.bytecode.opencsv.CSVReader;

@Component
public class DefaultLexer implements Lexer{
	public List<List<String>> tokenize(Reader reader) throws IOException{
		List<List<String>> lines = new ArrayList<List<String>>();
		CSVReader csvreader = new CSVReader(reader, ',', '"', 1);
		
		String [] nextLine;
		nextLine = csvreader.readNext();
		boolean hasNext = hasNext(nextLine);
		while (hasNext){
			lines.add(Arrays.asList(nextLine));
			nextLine = csvreader.readNext();
			hasNext = hasNext(nextLine);
		}
		return lines;
	}

	private boolean hasNext(String[] nextLine) {
		return nextLine != null;
	}
}
