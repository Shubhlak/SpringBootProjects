
package com.SR.PoleAppBackend.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.SR.PoleAppBackend.entity.Poll;
import com.SR.PoleAppBackend.Repository.PollRepository;

@Service
public class PollExpiryService {

    private final PollRepository pollRepository;

    public PollExpiryService(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

 
    
    @Scheduled(fixedRate = 60000)
    public void deactivateExpiredPolls() {
        LocalDateTime now = LocalDateTime.now();

        List<Poll> activePolls = pollRepository.findByStatus("active");
        for (Poll poll : activePolls) {
         
            LocalDateTime expiryTime = poll.getCreatedAT().plusHours(poll.getDurationHrs());
            if (now.isAfter(expiryTime)) {
                poll.setStatus("deactivated");
                pollRepository.save(poll);
                System.out.println("Poll deactivated: " + poll.getPollId());
            }
        }
    }

}
