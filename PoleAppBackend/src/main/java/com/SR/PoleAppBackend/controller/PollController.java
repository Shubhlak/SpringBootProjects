package com.SR.PoleAppBackend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.SR.PoleAppBackend.DTO.PollDTO;
import com.SR.PoleAppBackend.Service.PollService;
import com.SR.PoleAppBackend.entity.Poll;

@RestController
@RequestMapping("/api/polls")
@CrossOrigin  
public class PollController {
    
    @Autowired
    private PollService pollService;
    
    @Autowired
    private ModelMapper modelMapper;

    /**
     * GET: Retrieve all polls and convert them to PollDTO.
     */
    @GetMapping
    public List<PollDTO> getAllPolls() {
        List<Poll> polls = pollService.getAllPolls();
        return polls.stream()
                    .map(poll -> modelMapper.map(poll, PollDTO.class))
                    .collect(Collectors.toList());
    }
    
    /**
     * GET: Retrieve a single poll by its ID.
     */
    @GetMapping("/{pollId}")
    public PollDTO getPollById(@PathVariable Long pollId) {
        Poll poll = pollService.getPollById(pollId);
        return modelMapper.map(poll, PollDTO.class);
    }
    
    /**
     * GET: Retrieve polls by status.
     */
    @GetMapping("/status")
    public List<PollDTO> getPollsByStatus(@RequestParam("value") String status) {
        List<Poll> polls = pollService.getPollByStatus(status);
        return polls.stream()
                    .map(poll -> modelMapper.map(poll, PollDTO.class))
                    .collect(Collectors.toList());
    }
    
    /**
     * POST: Create a new poll. Converts the PollDTO to a Poll entity,
     * calls the service to create it, and then converts the result back to PollDTO.
     */
    @PostMapping
    public PollDTO createPoll(@RequestParam("userId") Long userId, @RequestBody PollDTO pollDTO) {
        // Convert DTO to entity
        Poll pollEntity = modelMapper.map(pollDTO, Poll.class);
        Poll createdPoll = pollService.createPoll(userId, pollEntity);
        // Convert the created entity back to DTO
        return modelMapper.map(createdPoll, PollDTO.class);
    }
    
    /**
     * DELETE: Delete a poll by ID.
     */
    @DeleteMapping("/{pollId}")
    public void deletePoll(@PathVariable Long pollId) {
        pollService.deletePoll(pollId);
    }
}
