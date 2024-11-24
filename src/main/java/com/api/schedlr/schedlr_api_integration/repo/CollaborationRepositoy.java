package com.api.schedlr.schedlr_api_integration.repo;

import com.api.schedlr.schedlr_api_integration.entity.Collaboration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CollaborationRepositoy extends JpaRepository<Collaboration, Integer> {
    Optional<Collaboration> findByUserIdAndInfluencerId(int userId, int influencerId);
}