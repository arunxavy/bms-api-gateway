package com.bms.gateway.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bms.gateway.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String userName);

}
