package com.SR.PoleAppBackend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SR.PoleAppBackend.entity.Poll;

public interface PollRepository extends JpaRepository<Poll, Long>{

	List<Poll> findByStatus(String status);
	List<Poll> findByUserUserId(Long userId);
	
}
