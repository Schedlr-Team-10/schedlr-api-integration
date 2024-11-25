package com.api.schedlr.schedlr_api_integration.repo;

import com.api.schedlr.schedlr_api_integration.entity.Collaboration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollaborationRepository extends JpaRepository<Collaboration, Integer> {
    Optional<Collaboration> findByUserIdAndInfluencerId(int userId, int influencerId);

    void deleteByUserIdAndInfluencerId(int userId, int influencerId);

    @Query("SELECT c, u.username FROM Collaboration c " +
            "JOIN User u ON c.userId = u.userid " +
            "WHERE c.influencerId = :influencerId")
    List<Object[]> findByInfluencerIdAndStatusWithUsernames(
            @Param("influencerId") int influencerId
    );
}