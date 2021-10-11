package com.autumnia.jwtservice.repositories;

import com.autumnia.jwtservice.entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
