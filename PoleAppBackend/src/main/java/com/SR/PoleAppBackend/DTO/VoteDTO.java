package com.SR.PoleAppBackend.DTO;

import java.util.List;


public class VoteDTO {
    private Long pollId;          
    private Long userId;        
    private String selectedOption; 
    private List<String> selectedOptions; 


    public Long getPollId() {
        return pollId;
    }
    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSelectedOption() {
        return selectedOption;
    }
    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public List<String> getSelectedOptions() {
        return selectedOptions;
    }
    public void setSelectedOptions(List<String> selectedOptions) {
        this.selectedOptions = selectedOptions;
    }
}
