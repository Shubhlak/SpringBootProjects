package com.SR.PoleAppBackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.SR.PoleAppBackend.Service.VoteService;
import com.SR.PoleAppBackend.entity.Vote;

@RestController
@RequestMapping("/api/votes")
@CrossOrigin
public class VoteController {

    @Autowired
    private VoteService voteService;

    /**
     * GET: Retrieve all votes
     */
    @GetMapping
    public List<Vote> getAllVotes() {
        return voteService.getAllVotes();
    }

    /**
     * GET: Retrieve vote by ID
     */
    @GetMapping("/{voteId}")
    public Vote getVoteById(@PathVariable Long voteId) {
        return voteService.getVoteById(voteId);
    }

    /**
     * GET: Retrieve all votes by a user
     * Example: GET /api/votes/user/10
     */
    @GetMapping("/user/{userId}")
    public List<Vote> getVotesByUser(@PathVariable Long userId) {
        return voteService.getVotesByUser(userId);
    }

    /**
     * POST: Cast a new vote
     */
    @PostMapping
    public Vote castVote(@RequestBody Vote vote) {
        return voteService.castVote(vote);
    }

    /**
     * DELETE: Delete a vote by ID
     */
    @DeleteMapping("/{voteId}")
    public void deleteVote(@PathVariable Long voteId) {
        voteService.deleteVote(voteId);
    }
}
