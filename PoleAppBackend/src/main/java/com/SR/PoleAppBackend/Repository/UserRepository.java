package com.SR.PoleAppBackend.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.SR.PoleAppBackend.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{

	User findByEmail(String email);
}
