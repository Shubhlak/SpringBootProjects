package com.SR.PoleAppBackend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying; // NEW: Import Modifying
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional; // NEW: Import Transactional

import com.SR.PoleAppBackend.entity.Poll;
import com.SR.PoleAppBackend.entity.User;
import com.SR.PoleAppBackend.entity.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    
    List<Vote> findByUser(User user);
     
    boolean existsByUserAndPoll(User user, Poll poll); // this method i added newly
     
   
    @Query("SELECT o.optionText, COUNT(v) FROM Option o LEFT JOIN o.votes v WHERE o.poll.pollId = :pollId GROUP BY o.optionText ORDER BY COUNT(v) DESC")
    List<Object[]> getVoteCountsByPoll(@Param("pollId") Long pollId);
    
    
    
    @Query("SELECT v FROM Vote v WHERE v.poll.pollId = :pollId")
    List<Vote> findVotesByPollId(@Param("pollId") Long pollId);
    
    // delete votes by pollId to avoid constraint violations when deleting a poll
//    @Modifying 
//    @Transactional 
//    void deleteByPollPollId(Long pollId);
    
    
    
    
    // this is trying someting new if failed to delete then revert back to code above this 
    
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.poll.pollId = :pollId")
    void deleteVotesByPollId(@Param("pollId") Long pollId);
    
    
    
    @Modifying
    @Transactional
    void deleteByUserAndPoll(User user, Poll poll);
}
