package edu.unlv.cs.rebelhotel.voters;

import java.util.Collection;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import edu.unlv.cs.rebelhotel.domain.Student;
import edu.unlv.cs.rebelhotel.domain.WorkEffort;
import edu.unlv.cs.rebelhotel.domain.enums.UserGroup;
import edu.unlv.cs.rebelhotel.service.UserInformation;

// simple voter test/experiment

public class ViewWorkEffortVoter implements AccessDecisionVoter {
	@Autowired
	UserInformation userInformation;
	
	public boolean supports(java.lang.Class<?> clazz) {
		return clazz.isAssignableFrom(MethodInvocation.class);
	}
	
	public boolean supports(ConfigAttribute attribute) {
		if (attribute.getAttribute() != null && attribute.getAttribute().equals("VIEW_WORK_EFFORT")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public int vote(Authentication authentication, java.lang.Object object, java.util.Collection<ConfigAttribute> attributes) {
		int result = ACCESS_ABSTAIN;
		
		Collection<GrantedAuthority> authorities = authentication.getAuthorities();
		
		for (ConfigAttribute attribute : attributes) {
			if (this.supports(attribute)) {
				// users more powerful than students can view any work efforts
				for (GrantedAuthority authority : authorities) {
					if (authority.getAuthority().equals("ROLE_USER") || authority.getAuthority().equals("ROLE_ADMIN") || authority.getAuthority().equals("ROLE_SUPERUSER")) {
						result = ACCESS_GRANTED;
						break;
					}
				}
				if (result == ACCESS_ABSTAIN) { // students themselves can only view their own work efforts
					if (userInformation.getStudent() != null) {
						Student accessor = userInformation.getStudent();
						MethodInvocation invocation = (MethodInvocation) object;
						Long id = (Long) invocation.getArguments()[0];
						WorkEffort job = WorkEffort.findWorkEffort(id);
						if (job == null) {
							return ACCESS_ABSTAIN;
						}
						Student owner = job.getStudent();
						if (accessor.getId().equals(owner.getId())) {
							result = ACCESS_GRANTED;
						}
						else {
							result = ACCESS_DENIED;
						}
					}
				}
			}
		}
		return result;
	}
}