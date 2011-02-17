package edu.unlv.cs.rebelhotel.domain;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;

@RooIntegrationTest(entity = WorkEffort.class)
public class WorkEffortIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }
    
    /*@Test
    public void testRemove() {
        edu.unlv.cs.rebelhotel.domain.WorkEffort obj = dod.getRandomWorkEffort();
        org.junit.Assert.assertNotNull("Data on demand for 'WorkEffort' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'WorkEffort' failed to provide an identifier", id);
        obj = edu.unlv.cs.rebelhotel.domain.WorkEffort.findWorkEffort(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'WorkEffort' with identifier '" + id + "'", edu.unlv.cs.rebelhotel.domain.WorkEffort.findWorkEffort(id));
    }*/
    
    @Test
    public void testRemove() {
    }
}
