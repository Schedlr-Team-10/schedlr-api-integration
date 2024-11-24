package com.api.schedlr.schedlr_api_integration.repo;

import com.api.schedlr.schedlr_api_integration.DTOs.InfluencerWithUserNameDTO;
import com.api.schedlr.schedlr_api_integration.entity.Influencer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InfluencerRepository extends JpaRepository<Influencer, Integer> {
    Influencer findByUserid(int userid);
}