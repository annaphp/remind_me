package com.remind_me.core.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author anna
 *
 */
@Entity
public class User implements UserDetails {
	
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Size(min=4, max=255, message="Username must be at least 4 chars long.")
	private String userName;
	
	@Size(min=8, max=512, message="Password must be at least 8 chars")
	private String password;
	
	private String email;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	private LocalDateTime emailVerified = LocalDateTime.MAX;
	private String verificationCode;
	private LocalDateTime createdDate;
	
	

	@PrePersist
	public void initVerification(){
		createdDate = LocalDateTime.now();
		verificationCode = UUID.randomUUID().toString();
	}
	
	public User(String username, String password, String email) {
		this.userName = username;
		this.password = password;
		this.email = email;
	}
	
	public User(){
	}


	
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getUserName() {
		return userName;
	}

	public LocalDateTime getEmailVerified() {
		return emailVerified;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setEmailVerified(LocalDateTime emailVerified) {
		this.emailVerified = emailVerified;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return userName;
	}

	public void setUsername(String username) {
		this.userName = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + userName + ", password=" + password + ", email=" + email + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(role.toString()));
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {	
		return emailVerified.isBefore(LocalDateTime.now());
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

		
}
