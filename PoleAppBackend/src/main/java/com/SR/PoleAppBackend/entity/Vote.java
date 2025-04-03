package com.SR.PoleAppBackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="votes")
public class Vote {
	
	

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long voteId;
	
	
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	
	
	
	@ManyToOne
	@JoinColumn(name = "poll_id", nullable = false)
	@JsonBackReference(value = "poll-votes")
	private Poll poll;
	
	
	@ManyToOne
	@JoinColumn(name = "option_id", nullable = false)
	private Option option;
	
	
	 public Vote() {}


	public Long getVoteId() {
		return voteId;
	}


	public void setVoteId(Long voteId) {
		this.voteId = voteId;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Poll getPoll() {
		return poll;
	}


	public void setPoll(Poll poll) {
		this.poll = poll;
	}


	public Option getOption() {
		return option;
	}


	public void setOption(Option option) {
		this.option = option;
	}
	 
	 
	
	
	
	

}
