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
	
	private final UserAccount account;
	
	public RebelUserDetails(UserAccount account) {
		this.account = account;
	}
	
	public Collection<GrantedAuthority> getAuthorities() {
		return account.getUserGroup().getGranted();
	}
	
	public String getPassword() {
		return account.getPassword();
	}
	
	public String getUsername() {
		return account.getUserId().toString();
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
		return account.getEnabled();
	}
}