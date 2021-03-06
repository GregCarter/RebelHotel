// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package edu.unlv.cs.rebelhotel.domain;

import edu.unlv.cs.rebelhotel.domain.Term;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

privileged aspect TermDataOnDemand_Roo_DataOnDemand {
    
    declare @type: TermDataOnDemand: @Component;
    
    private Random TermDataOnDemand.rnd = new java.security.SecureRandom();
    
    private List<Term> TermDataOnDemand.data;
    
    public Term TermDataOnDemand.getNewTransientTerm(int index) {
        edu.unlv.cs.rebelhotel.domain.Term obj = new edu.unlv.cs.rebelhotel.domain.Term();
        obj.setTermYear(new Integer(index));
        obj.setSemester(null);
        return obj;
    }
    
    public Term TermDataOnDemand.getSpecificTerm(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Term obj = data.get(index);
        return Term.findTerm(obj.getId());
    }
    
    public Term TermDataOnDemand.getRandomTerm() {
        init();
        Term obj = data.get(rnd.nextInt(data.size()));
        return Term.findTerm(obj.getId());
    }
    
    public boolean TermDataOnDemand.modifyTerm(Term obj) {
        return false;
    }
    
    public void TermDataOnDemand.init() {
        data = edu.unlv.cs.rebelhotel.domain.Term.findTermEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Term' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new java.util.ArrayList<edu.unlv.cs.rebelhotel.domain.Term>();
        for (int i = 0; i < 10; i++) {
            edu.unlv.cs.rebelhotel.domain.Term obj = getNewTransientTerm(i);
            obj.persist();
            obj.flush();
            data.add(obj);
        }
    }
    
}
