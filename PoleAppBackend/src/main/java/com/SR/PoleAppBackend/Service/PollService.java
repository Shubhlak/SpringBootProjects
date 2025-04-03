package com.SR.PoleAppBackend.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // NEW: Import Transactional

import com.SR.PoleAppBackend.Repository.PollRepository;
import com.SR.PoleAppBackend.Repository.UserRepository;
import com.SR.PoleAppBackend.Repository.VoteRepository; // NEW: Import VoteRepository for deleting associated votes
import com.SR.PoleAppBackend.entity.Option;
import com.SR.PoleAppBackend.entity.Poll;
import com.SR.PoleAppBackend.entity.User;
import com.SR.PoleAppBackend.entity.Vote;

@Service
public class PollService {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private UserRepository userRepository;
    
   
    @Autowired
    private VoteRepository voteRepository;

    // Create a new poll
    public Poll createPoll(Long userId, Poll poll) {
       
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        poll.setUser(user);
        
        
        poll.setCreatedAT(LocalDateTime.now());
        poll.setStatus("active");
        
        
        if (poll.getOptions() != null) {
            for (Option option : poll.getOptions()) {
                option.setPoll(poll);
            }
        }
    
        return pollRepository.save(poll);
     }
    

    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }
    
    public Poll getPollById(Long pollId) {
        return pollRepository.findById(pollId)
                .orElseThrow(() -> new RuntimeException("Poll not found with ID: " + pollId));
    }
    
    
    public List<Poll> getPollByStatus(String status) {
        return pollRepository.findByStatus(status);
    }
    
   
    public List<Poll> getPollsByUserId(Long userId) {
        return pollRepository.findByUserUserId(userId);
    }
    
   
    public List<Poll> searchPolls(String search) {
        if (search == null || search.isEmpty()) {
            return getAllPolls();
        }
        String searchLower = search.toLowerCase();
        return pollRepository.findAll().stream()
            .filter(p -> p.getTitle().toLowerCase().contains(searchLower) ||
                         p.getQuestion().toLowerCase().contains(searchLower))
            .collect(Collectors.toList());
    }
    
    public Map<String, List<String>> getPollStatistics(Long pollId) {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new RuntimeException("Poll not found with ID: " + pollId));
        List<Vote> votes = voteRepository.findVotesByPollId(pollId); 
        return votes.stream()
                .collect(Collectors.groupingBy(
                    vote -> vote.getOption().getOptionText(), 
                    Collectors.mapping(vote -> vote.getUser().getFullName(), Collectors.toList())
                ));
    }
    
    
    @Transactional
    public void deletePoll(Long pollId) {
        voteRepository.deleteVotesByPollId(pollId); 
        pollRepository.deleteById(pollId);
    }
}