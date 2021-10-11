package com.autumnia.jwtservice.repositories;

import com.autumnia.jwtservice.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	// lazy 조회가 아닌 eager 조회로 authorities 정보를 가져온다. 
	@EntityGraph(attributePaths = "authorities")
	Optional<User> findOneWithAuthoritiesByUsername(String username);
}
