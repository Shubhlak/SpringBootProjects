package com.SR.PoleAppBackend.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;


@Entity
@Table(name="polls_table")
public class Poll {
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pollId;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	
	private String title;
	private String question;
	private String pollType; // multi , single
	private LocalDateTime createdAT;
	private int durationHrs;
	private String status;  // activated , deactivated 
	
	
	// A poll can have many options.
	@OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Option> options;
	
	// A poll can have many votes.
	@OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Vote> votes;
	
	
	
	public Poll() {}



	public Long getPollId() {
		return pollId;
	}



	public void setPollId(Long pollId) {
		this.pollId = pollId;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getQuestion() {
		return question;
	}



	public void setQuestion(String question) {
		this.question = question;
	}



	public String getPollType() {
		return pollType;
	}



	public void setPollType(String pollType) {
		this.pollType = pollType;
	}



	public LocalDateTime getCreatedAT() {
		return createdAT;
	}



	public void setCreatedAT(LocalDateTime createdAT) {
		this.createdAT = createdAT;
	}



	public int getDurationHrs() {
		return durationHrs;
	}



	public void setDurationHrs(int durationHrs) {
		this.durationHrs = durationHrs;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public List<Option> getOptions() {
		return options;
	}



	public void setOptions(List<Option> options) {
		this.options = options;
	}



	public List<Vote> getVotes() {
		return votes;
	}



	public void setVotes(List<Vote> votes) {
		this.votes = votes;
	}
	
	

	
	
	

}
