package com.api.schedlr.schedlr_api_integration.repo;

import com.api.schedlr.schedlr_api_integration.entity.Profile;
import com.api.schedlr.schedlr_api_integration.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    // Use this method to find by userId
    Optional<Profile> findByUserId(int userId);

    @Query("SELECT p.linkedInAccessToken, p.linkedInPersonId FROM Profile p WHERE p.userId = :userId")
    List<Object[]> findLinkedInTokenAndPersonIdByUserId(int userId);
}