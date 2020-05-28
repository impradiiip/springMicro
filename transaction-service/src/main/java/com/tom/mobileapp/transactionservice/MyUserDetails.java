package com.tom.mobileapp.transactionservice;



import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



public class MyUserDetails implements UserDetails {

	private String userName;
	private String password;
	private boolean active;
	private List<GrantedAuthority> authorities;
	
	

	/*
	 * public MyUserDetails(User user) { this.userName = user.getName();
	 * this.password = user.getPassword(); this.active = user.getIsActive();
	 * this.authorities =
	 * Arrays.stream(user.getRoles().split(",")).map(SimpleGrantedAuthority::new)
	 * .collect(Collectors.toList()); }
	 */
	/*
	 * public MyUserDetails(User user) { this.userName = user.getName();
	 * this.password = user.getPassword(); this.active = user.getIsActive();
	 * this.authorities =
	 * Arrays.stream(user.getRoles().split(",")).map(SimpleGrantedAuthority::new)
	 * .collect(Collectors.toList()); }
	 */

	public MyUserDetails(String userName, String password, boolean active, List<GrantedAuthority> authorities) {
		super();
		this.userName = userName;
		this.password = password;
		this.active = active;
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}

}
