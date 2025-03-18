package com.SR.PoleAppBackend.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SR.PoleAppBackend.Repository.UserRepository;
import com.SR.PoleAppBackend.Repository.VoteRepository;
import com.SR.PoleAppBackend.entity.User;
import com.SR.PoleAppBackend.entity.Vote;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    // Retrieve all votes
    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    // Retrieve vote by ID
    public Vote getVoteById(Long voteId) {
        return voteRepository.findById(voteId)
                .orElseThrow(() -> new RuntimeException("Vote not found with ID: " + voteId));
    }

    // Retrieve all votes by a given user
    public List<Vote> getVotesByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return voteRepository.findByUser(user);
    }

    // Cast a new vote
    public Vote castVote(Vote vote) {
        // Additional checks can be done here (e.g., preventing multiple votes)
        return voteRepository.save(vote);
    }

    // Delete a vote by ID
    public void deleteVote(Long voteId) {
        voteRepository.deleteById(voteId);
    }
}
