package com.SR.PoleAppBackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "options")
public class Option {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long optionId;
	
	@ManyToOne()
	@JoinColumn(name = "poll_id", nullable = false)
	private Poll poll;
	
	
	private String optionText;
	
	
	public Option() {}


	public Long getOptionId() {
		return optionId;
	}


	public void setOptionId(Long optionId) {
		this.optionId = optionId;
	}


	public Poll getPoll() {
		return poll;
	}


	public void setPoll(Poll poll) {
		this.poll = poll;
	}


	public String getOptionText() {
		return optionText;
	}


	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}
	
	
	

}
