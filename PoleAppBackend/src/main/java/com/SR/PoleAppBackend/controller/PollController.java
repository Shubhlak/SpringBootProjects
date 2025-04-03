package com.SR.PoleAppBackend.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.SR.PoleAppBackend.DTO.PollDTO;
import com.SR.PoleAppBackend.Service.PollService;
import com.SR.PoleAppBackend.Service.VoteService;
import com.SR.PoleAppBackend.entity.Poll;
import com.SR.PoleAppBackend.entity.User;
import com.SR.PoleAppBackend.Repository.UserRepository;

@RestController
@RequestMapping("/api/polls")
@CrossOrigin
public class PollController {

    @Autowired
    private PollService pollService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VoteService voteService;

    @GetMapping
    public List<PollDTO> getAllPolls(
        @RequestParam(required = false) Long userId,
        @RequestParam(required = false) Long creatorId,
        @RequestParam(required = false) String search  
    ) {
        List<Poll> polls;
        if (creatorId != null) {
            polls = pollService.getPollsByUserId(creatorId);
        } else if (search != null && !search.isEmpty()) {
            polls = pollService.searchPolls(search);  
        } else {
            polls = pollService.getAllPolls();
        }
        
        final Set<Long> votedPollIds = (userId != null) ?
            voteService.getVotesByUser(userId).stream()
                .map(vote -> vote.getPoll().getPollId())
                .collect(Collectors.toSet()) :
            Collections.emptySet();
        
        return polls.stream()
                    .map(poll -> {
                        PollDTO dto = modelMapper.map(poll, PollDTO.class);
                        Long userIdFromPoll = poll.getUser().getUserId();
                        User creator = userRepository.findById(userIdFromPoll).orElse(null);
                        if (creator != null) {
                            dto.setCreatedBy(creator.getFullName());
                            dto.setCreatedByUserId(creator.getUserId());
                        } else {
                            dto.setCreatedBy("Unknown");
                            dto.setCreatedByUserId(null);
                        }
                        dto.setHasVoted(votedPollIds.contains(poll.getPollId()));
                        return dto;
                    })
                    .collect(Collectors.toList());
    }

 



    @PostMapping
    public PollDTO createPoll(@RequestParam("userId") Long userId, @RequestBody PollDTO pollDTO) {
        Poll pollEntity = modelMapper.map(pollDTO, Poll.class);
        Poll createdPoll = pollService.createPoll(userId, pollEntity);
        PollDTO createdDTO = modelMapper.map(createdPoll, PollDTO.class);
        User creator = userRepository.findById(userId).orElse(null);
        if (creator != null) {
            createdDTO.setCreatedBy(creator.getFullName());
            createdDTO.setCreatedByUserId(creator.getUserId());
        } else {
            createdDTO.setCreatedBy("Unknown");
            createdDTO.setCreatedByUserId(null);
        }
        return createdDTO;
    }

    @GetMapping("/{pollId}/statistics")
    public Map<String, List<String>> getPollStatistics(@PathVariable Long pollId) {
        return pollService.getPollStatistics(pollId);
    }
    
    @DeleteMapping("/{pollId}")
    public void deletePoll(@PathVariable Long pollId) {
        pollService.deletePoll(pollId);
    }
}