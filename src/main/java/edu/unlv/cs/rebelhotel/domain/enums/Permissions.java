package edu.unlv.cs.rebelhotel.domain.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Permissions implements GrantedAuthority {
    READ, WRITE;
    
    public String getAuthority() {
    	return this.toString();
    }
}
