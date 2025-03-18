package com.SR.PoleAppBackend.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SR.PoleAppBackend.Repository.PollRepository;
import com.SR.PoleAppBackend.Repository.UserRepository;
import com.SR.PoleAppBackend.entity.Option;
import com.SR.PoleAppBackend.entity.Poll;
import com.SR.PoleAppBackend.entity.User;

@Service
public class PollService {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private UserRepository userRepository;

    // Create a new poll
    public Poll createPoll(Long userId, Poll poll) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        poll.setUser(user);
        poll.setCreatedAT(LocalDateTime.now());
        poll.setStatus("active");
        
        // Set the poll reference for each option, if options exis
        if (poll.getOptions() != null) {
            for (Option option : poll.getOptions()) {
                option.setPoll(poll);
            }
        }
    
        return pollRepository.save(poll);
     }
    
    
    
    
    

    // Get all polls
    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }
    
    
    
    
    

    // Get a poll by ID
    public Poll getPollById(Long pollId) {
        return pollRepository.findById(pollId)
                .orElseThrow(() -> new RuntimeException("Poll not found with ID: " + pollId));
    }
    
    
    
    

    // Get polls by status (active or deactivated)
    public List<Poll> getPollByStatus(String status) {
        return pollRepository.findByStatus(status);
    }

    
    
    
    // Delete a poll by ID
    public void deletePoll(Long pollId) {
        pollRepository.deleteById(pollId);
    }
}
