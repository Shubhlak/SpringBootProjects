package com.SR.PoleAppBackend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="users")
public class User {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	private String fullName;
	
	@Column(unique = true, nullable = false, length = 100)
	private String email;
	private String password;
	private Boolean loggedInStatus;
	//this is for mail
	private String resetOtp;
	private LocalDateTime resetOtpExpiry;
	
	
	public User() {}
	
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getLoggedInStatus() {
		return loggedInStatus;
	}
	public void setLoggedInStatus(Boolean loggedInStatus) {
		this.loggedInStatus = loggedInStatus;
	}
	
	public String getResetOtp() {
	        return resetOtp;
	    }
	 
	 
	public void setResetOtp(String resetOtp) {
	        this.resetOtp = resetOtp;
	    }

	public LocalDateTime getResetOtpExpiry() {
	        return resetOtpExpiry;
	    }

	public void setResetOtpExpiry(LocalDateTime resetOtpExpiry) {
	        this.resetOtpExpiry = resetOtpExpiry;
	    }
	

}
