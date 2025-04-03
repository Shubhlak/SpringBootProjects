package com.SR.PoleAppBackend.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SR.PoleAppBackend.DTO.OptionDTO;
import com.SR.PoleAppBackend.DTO.OptionStatsDTO;
import com.SR.PoleAppBackend.DTO.VoteDTO;
import com.SR.PoleAppBackend.DTO.VoteHistoryDTO; // NEW: Import the new DTO
import com.SR.PoleAppBackend.Repository.PollRepository;
import com.SR.PoleAppBackend.Repository.UserRepository;
import com.SR.PoleAppBackend.Repository.VoteRepository;
import com.SR.PoleAppBackend.entity.Option;
import com.SR.PoleAppBackend.entity.Poll;
import com.SR.PoleAppBackend.entity.User;
import com.SR.PoleAppBackend.entity.Vote;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    
    @Autowired
    private PollRepository pollRepository;
    

    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }


    public Vote getVoteById(Long voteId) {
        return voteRepository.findById(voteId)
                .orElseThrow(() -> new RuntimeException("Vote not found with ID: " + voteId));
    }

    public List<Vote> getVotesByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return voteRepository.findByUser(user);
    }


    public List<VoteHistoryDTO> getVoteHistoryByUser(Long userId) {
        List<Vote> votes = getVotesByUser(userId);
        return votes.stream().map(vote -> {
            VoteHistoryDTO dto = new VoteHistoryDTO();
            dto.setVoteId(vote.getVoteId());
            dto.setPollId(vote.getPoll().getPollId());
            dto.setPollTitle(vote.getPoll().getTitle());
            dto.setPollQuestion(vote.getPoll().getQuestion());
            // NEW: Set the poll status
            dto.setStatus(vote.getPoll().getStatus());
            List<OptionDTO> optionDTOs = vote.getPoll().getOptions().stream()
                .map(opt -> new OptionDTO(opt.getOptionId(), opt.getOptionText()))
                .collect(Collectors.toList());
            dto.setOptions(optionDTOs);
            dto.setSelectedOptionText(vote.getOption().getOptionText());
            return dto;
        }).collect(Collectors.toList());
    }

  
    public boolean hasUserVoted(Long userId, Long pollId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new RuntimeException("Poll not found with ID: " + pollId));
        return voteRepository.existsByUserAndPoll(user, poll);
    }

    //  single choice
    public Vote castSingleChoiceVote(VoteDTO voteDTO) {
        Poll poll = pollRepository.findById(voteDTO.getPollId())
                .orElseThrow(() -> new RuntimeException("Poll not found with id: " + voteDTO.getPollId()));
        User user = userRepository.findById(voteDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + voteDTO.getUserId()));
        Option selectedOption = poll.getOptions().stream()
            .filter(opt -> opt.getOptionText().equals(voteDTO.getSelectedOption()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Option not found: " + voteDTO.getSelectedOption()));
        Vote vote = new Vote();
        vote.setPoll(poll);
        vote.setUser(user);
        vote.setOption(selectedOption);
        return voteRepository.save(vote);
    }
   

    // multiple choice
    public List<Vote> castMultipleChoiceVote(VoteDTO voteDTO) {
        Poll poll = pollRepository.findById(voteDTO.getPollId())
                .orElseThrow(() -> new RuntimeException("Poll not found with id: " + voteDTO.getPollId()));
        User user = userRepository.findById(voteDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + voteDTO.getUserId()));
        List<Vote> votes = new ArrayList<>();
        for (String optionText : voteDTO.getSelectedOptions()) {
            Option selectedOption = poll.getOptions().stream()
                .filter(opt -> opt.getOptionText().equals(optionText))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Option not found: " + optionText));
            Vote vote = new Vote();
            vote.setPoll(poll);
            vote.setUser(user);
            vote.setOption(selectedOption);
            Vote savedVote = voteRepository.save(vote);
            votes.add(savedVote);
        }
        return votes;
    }
   

    // Delete a vote by ID
    public void deleteVote(Long voteId) {
        voteRepository.deleteById(voteId);
    }
   
    public void deleteVotesByUserAndPoll(Long userId, Long pollId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new RuntimeException("Poll not found with ID: " + pollId));
        voteRepository.deleteByUserAndPoll(user, poll);
    }

    //get poll statistics as a list of OptionStatsDTO
    public List<OptionStatsDTO> getPollStatistics(Long pollId) {
        List<Object[]> results = voteRepository.getVoteCountsByPoll(pollId);
        return results.stream()
            .map(result -> {
                String optionText = (String) result[0];
                Long count = (Long) result[1];
                OptionStatsDTO dto = new OptionStatsDTO();
                dto.setOptionText(optionText);
                dto.setVoteCount(count);
                return dto;
            })
            .collect(Collectors.toList());
    }
}