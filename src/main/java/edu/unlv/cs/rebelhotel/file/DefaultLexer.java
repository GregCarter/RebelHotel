package edu.unlv.cs.rebelhotel.file;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import au.com.bytecode.opencsv.CSVReader;

@Component
public class DefaultLexer implements Lexer{
	public List<List<String>> tokenize(Reader reader){
		List<String> tokens = new ArrayList<String>();
		List<List<String>> lines = new ArrayList<List<String>>();
		
		CSVReader csvreader = new CSVReader(reader);
		
		
		
		return lines;
	}
}
