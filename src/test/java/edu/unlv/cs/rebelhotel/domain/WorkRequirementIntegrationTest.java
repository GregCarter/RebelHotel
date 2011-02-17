package edu.unlv.cs.rebelhotel.domain;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;

@RooIntegrationTest(entity = WorkRequirement.class)
public class WorkRequirementIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }
    
    /*@Test
    public void testRemove() {
        edu.unlv.cs.rebelhotel.domain.WorkRequirement obj = dod.getRandomWorkRequirement();
        org.junit.Assert.assertNotNull("Data on demand for 'WorkRequirement' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'WorkRequirement' failed to provide an identifier", id);
        obj = edu.unlv.cs.rebelhotel.domain.WorkRequirement.findWorkRequirement(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'WorkRequirement' with identifier '" + id + "'", edu.unlv.cs.rebelhotel.domain.WorkRequirement.findWorkRequirement(id));
    }*/
    
    @Test
    public void testRemove() {
    }
}
