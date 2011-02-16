package edu.unlv.cs.rebelhotel.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import edu.unlv.cs.rebelhotel.domain.UserAccount;

public class RebelUserDetails implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3948746334635349003L;
	
	private final UserAccount ua;
	
	public RebelUserDetails(UserAccount ua) {
		this.ua = ua;
	}
	
	public Collection<GrantedAuthority> getAuthorities() {
		return ua.getUserGroup().getGranted();
	}
	
	public String getPassword() {
		return ua.getPassword();
	}
	
	public String getUsername() {
		return ua.getUserId().toString();
	}
	
	public boolean isAccountNonExpired() {
		return true;
	}
	
	public boolean isAccountNonLocked() {
		return true;
	}
	
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	public boolean isEnabled() {
		return ua.getEnabled();
	}
}