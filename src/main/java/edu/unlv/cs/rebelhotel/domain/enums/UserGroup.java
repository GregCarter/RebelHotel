package edu.unlv.cs.rebelhotel.domain.enums;

import org.springframework.security.core.GrantedAuthority;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Set;

public enum UserGroup implements GrantedAuthority {
	ROLE_STUDENT(EnumSet.of(Permissions.READ)), // = student (view progress toward degree)
    ROLE_USER(EnumSet.of(Permissions.READ)), // = read only access (not a student)
    ROLE_ADMIN(EnumSet.of(Permissions.READ, Permissions.WRITE)), // = staff
    ROLE_SUPERUSER(EnumSet.of(Permissions.READ, Permissions.WRITE)); // = root user
    
    private final Set<Permissions> permissions;
    
    private UserGroup(Set<Permissions> permissions) {
    	this.permissions = permissions;
    }
    
    public String getAuthority() {
    	return this.toString();
    }
    
    public Set<GrantedAuthority> getGranted() {
    	Set<GrantedAuthority> granted = new HashSet<GrantedAuthority>();
    	granted.add(this);
    	for (Permissions permission: permissions) {
    		granted.add(permission);
    	}
    	return granted;
    }
}
