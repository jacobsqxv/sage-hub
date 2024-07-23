package dev.aries.sagehub.security;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import dev.aries.sagehub.model.User;
import lombok.AllArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

	private final transient User user;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(String.valueOf(this.user.getRole().getName())));
	}

	public UUID getClientId() {
		return this.user.getClientId();
	}

	@Override
	public String getPassword() {
		return this.user.getHashedPassword();
	}

	@Override
	public String getUsername() {
		return this.user.getUsername();
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
		return this.user.isAccountEnabled();
	}
}
