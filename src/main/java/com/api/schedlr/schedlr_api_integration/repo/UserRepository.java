package com.api.schedlr.schedlr_api_integration.repo;

import com.api.schedlr.schedlr_api_integration.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserid(int userid);
}