package com.SR.PoleAppBackend.DTO;

import java.util.List;

public class VoteHistoryDTO {
    private Long voteId;
    private Long pollId;
    private String pollTitle;
    private String pollQuestion;
    private String status;
    private List<OptionDTO> options; 
    private String selectedOptionText;

    
    public VoteHistoryDTO() {}


    public Long getVoteId() {
        return voteId;
    }

    public void setVoteId(Long voteId) {
        this.voteId = voteId;
    }

    public Long getPollId() {
        return pollId;
    }

    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }

    public String getPollTitle() {
        return pollTitle;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setPollTitle(String pollTitle) {
        this.pollTitle = pollTitle;
    }

    public String getPollQuestion() {
        return pollQuestion;
    }

    public void setPollQuestion(String pollQuestion) {
        this.pollQuestion = pollQuestion;
    }

    public List<OptionDTO> getOptions() {
        return options;
    }

    public void setOptions(List<OptionDTO> options) {
        this.options = options;
    }

    public String getSelectedOptionText() {
        return selectedOptionText;
    }

    public void setSelectedOptionText(String selectedOptionText) {
        this.selectedOptionText = selectedOptionText;
    }
}