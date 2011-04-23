package edu.unlv.cs.rebelhotel.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.staticmock.MockStaticEntityMethods;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.test.annotation.NotTransactional;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import edu.unlv.cs.rebelhotel.domain.Student;
import edu.unlv.cs.rebelhotel.domain.StudentDataOnDemand;
import edu.unlv.cs.rebelhotel.domain.UserAccount;
import edu.unlv.cs.rebelhotel.domain.enums.UserGroup;
import edu.unlv.cs.rebelhotel.form.FormStudentQuery;
import edu.unlv.cs.rebelhotel.service.StudentQueryService;

import static org.easymock.EasyMock.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
@Transactional
//@MockStaticEntityMethods
public class StudentQueryTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private StudentQueryService queryService;
	
	@Autowired
	StudentDataOnDemand data;
	
	public static boolean setup = false;
	
	@Before
	@Rollback(false)
	public void setup() {
		if (!setup) {
			Student student1 = new Student();
			student1.setUserId("1008234235");
			student1.setFirstName("Jonathan");
			student1.setLastName("Wright");
			student1.setMiddleName("William");
			student1.setGradTerm(null);
			student1.setAdmitTerm(null);
			student1.setLastModified(new Date());
			student1.setCodeOfConductSigned(true);
			
			UserAccount user1 = new UserAccount();
			user1.setPasswordEncoder(new MessageDigestPasswordEncoder("SHA-256"));
			user1.setEmail("jwright@rebelhotel.edu");
			user1.setEnabled(true);
			user1.setPassword("fail");
			user1.setUserGroup(UserGroup.ROLE_STUDENT);
			user1.setUserId("1008234235");
			user1.persist();
			user1.flush();
			
			student1.setUserAccount(user1);
			student1.persist();
			student1.flush();
			
			setup = true;
		}
	}
	
	@Test
	public void testUserIdSearch() throws Exception {
		FormStudentQuery form = createMock(FormStudentQuery.class);
		
		expect(form.getUseUserId()).andReturn(true);
		expect(form.getUserId()).andReturn("1008234235");
		
		expect(form.getUseModified()).andReturn(false);
		expect(form.getUseCatalogTerm()).andReturn(false);
		expect(form.getUseGradTerm()).andReturn(false);
		expect(form.getUseMilestone()).andReturn(false);
		expect(form.getUseMajor()).andReturn(false);
		expect(form.getUseFirstName()).andReturn(false);
		expect(form.getUseMiddleName()).andReturn(false);
		expect(form.getUseLastName()).andReturn(false);
		expect(form.getUseHours()).andReturn(false);
		expect(form.getStudentUseHours()).andReturn(false);
		expect(form.getShowUserId()).andReturn(false);
		expect(form.getShowFirstName()).andReturn(false);
		expect(form.getShowMiddleName()).andReturn(false);
		expect(form.getShowLastName()).andReturn(false);
		expect(form.getShowAdmitTerm()).andReturn(false);
		expect(form.getShowGradTerm()).andReturn(false);
		expect(form.getShowCodeOfConductSigned()).andReturn(false);
		expect(form.getShowLastModified()).andReturn(false);
		expect(form.getShowUserAccount()).andReturn(false);
		expect(form.getVerificationSelected()).andReturn(false);
		expect(form.getValidationSelected()).andReturn(false);
		
		expect(form.getEmployerName()).andReturn("");
		expect(form.getEmployerLocation()).andReturn("");
		expect(form.getWorkEffortStartDate()).andReturn(null);
		expect(form.getWorkEffortEndDate()).andReturn(null);
		replay(form);
		
		List result = null;
		try {
			result = queryService.queryStudentsLimited(form, "0", 0, 25);
		}
		catch (Exception e) {
			fail("There was an error with the query");
		}
		Long resultCount = (Long) result.get(0);
		List<Student> students = (List<Student>) result.get(1);
		
		assertEquals("Number of student results should be one.", new Long(1), resultCount);
		assertTrue("Size of returned result is not greater than 0", resultCount > 0);
		assertTrue("Size of list is not greater than 0", students.size() > 0);
		assertEquals("Returned student result should have a user ID of 1008234235", new String("1008234235"), students.get(0).getUserId());
	}
	
	@After
	@Rollback(false)
	public void destroy() {
		Student student1 = Student.findStudentsByUserIdEquals("1008234235").getSingleResult();
		UserAccount user1 = UserAccount.findUserAccountsByUserId("1008234235").getSingleResult();
		student1.remove();
		user1.remove();
	}
}