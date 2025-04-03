package com.SR.PoleAppBackend.controller;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.SR.PoleAppBackend.DTO.OptionStatsDTO;
import com.SR.PoleAppBackend.DTO.VoteDTO;
import com.SR.PoleAppBackend.DTO.VoteHistoryDTO; // NEW: Import the new DTO
import com.SR.PoleAppBackend.Service.VoteService;
import com.SR.PoleAppBackend.entity.Vote;

@RestController
@RequestMapping("/api/votes")
@CrossOrigin
public class VoteController {

    @Autowired
    private VoteService voteService;

  
    @GetMapping
    public List<Vote> getAllVotes() {
        return voteService.getAllVotes();
    }

  
    @GetMapping("/{voteId}")
    public Vote getVoteById(@PathVariable Long voteId) {
        return voteService.getVoteById(voteId);
    }


    @GetMapping("/user/{userId}")
    public List<VoteHistoryDTO> getVotesByUser(@PathVariable Long userId) {
        List<VoteHistoryDTO> voteHistory = voteService.getVoteHistoryByUser(userId);
        return voteHistory;
    }


    @PostMapping
    public List<Vote> castVote(@RequestBody VoteDTO voteDTO) {
        Long userId = voteDTO.getUserId();
        Long pollId = voteDTO.getPollId();
       
        if (voteService.hasUserVoted(userId, pollId)) {
            throw new IllegalStateException("User has already voted on this poll");
        }
        // Single-choice
        if (voteDTO.getSelectedOption() != null) {
            Vote savedVote = voteService.castSingleChoiceVote(voteDTO);
            List<Vote> votes = new ArrayList<>();
            votes.add(savedVote);
            return votes;
        }
        // Multiple-choice
        else if (voteDTO.getSelectedOptions() != null && !voteDTO.getSelectedOptions().isEmpty()) {
            return voteService.castMultipleChoiceVote(voteDTO);
        }
     
        else {
            throw new IllegalArgumentException("No vote selection provided");
        }
    }
    
   
    @DeleteMapping("/{voteId}")
    public void deleteVote(@PathVariable Long voteId) {
        voteService.deleteVote(voteId);
    }


    @GetMapping("/stats/{pollId}")
    public List<OptionStatsDTO> getPollStatistics(@PathVariable Long pollId) {
        return voteService.getPollStatistics(pollId);
    }
    
    
    @DeleteMapping("/user/{userId}/poll/{pollId}")
    public void deleteVotesByUserAndPoll(@PathVariable Long userId, @PathVariable Long pollId) {
        voteService.deleteVotesByUserAndPoll(userId, pollId);
    }
    
    
    
}