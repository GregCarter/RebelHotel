package edu.unlv.cs.rebelhotel.file;


import org.junit.After;
import org.junit.Before;
import static org.easymock.EasyMock.*;

public class DefaultParserTest {
	private DefaultParser instance;
	private FileStudent fileStudent;
	
	@Before
	public void setUp() throws Exception {
		fileStudent = createMock(FileStudent.class);
	}

	@After
	public void tearDown() throws Exception {
	}


}
