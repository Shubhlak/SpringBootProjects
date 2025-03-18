package com.SR.PoleAppBackend.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PollDTO {
	
	private Long pollId;
	private String title;
	private String question;
	private String pollType;
	private LocalDateTime createdAt;
	private int durationHrs;
	private String status;
	private List<OptionDTO> options;
	
	
	public PollDTO() {}


	public Long getPollId() {
		return pollId;
	}


	public void setPollId(Long pollId) {
		this.pollId = pollId;
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


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
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


	public List<OptionDTO> getOptions() {
		return options;
	}


	public void setOptions(List<OptionDTO> options) {
		this.options = options;
	}
	
	
	

}
