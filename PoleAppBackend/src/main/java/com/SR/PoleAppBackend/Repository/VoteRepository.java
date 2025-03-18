package com.SR.PoleAppBackend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SR.PoleAppBackend.entity.User;
import com.SR.PoleAppBackend.entity.Vote;

public interface VoteRepository  extends JpaRepository<Vote, Long>{

	
	 List<Vote> findByUser(User user);
}
