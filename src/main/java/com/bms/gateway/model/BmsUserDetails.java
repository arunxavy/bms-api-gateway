package com.bms.gateway.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BmsUserDetails implements UserDetails {

	private static final long serialVersionUID = 275347623L;
	private Long id;
	private String username;
	private String password;
	private boolean active;
	private List<GrantedAuthority> authorities;

	public BmsUserDetails(User user) {
		this.id = user.getUserId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.active = user.isActive();

		this.authorities = Arrays.stream(user.getRoles().split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	@Override
	public boolean isAccountNonExpired() {

		return active;
	}

	@Override
	public boolean isAccountNonLocked() {
		return active;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return active;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}

}
