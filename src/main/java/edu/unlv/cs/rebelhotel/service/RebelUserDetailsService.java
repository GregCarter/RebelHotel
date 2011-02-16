package edu.unlv.cs.rebelhotel.service;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.unlv.cs.rebelhotel.domain.UserAccount;
//import edu.unlv.cs.rebelhotel.service.RebelUserDetails;

@Service("userService")
public class RebelUserDetailsService implements UserDetailsService {
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		UserAccount ua = (UserAccount) UserAccount.findUserAccountsByUserId(username).getSingleResult();
		UserDetails ud = new RebelUserDetails(ua);
		return ud;
	}
}