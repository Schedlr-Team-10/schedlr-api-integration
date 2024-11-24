package com.api.schedlr.schedlr_api_integration.repo;

import com.api.schedlr.schedlr_api_integration.entity.SchedulePostUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulePostUploadRepository extends JpaRepository<SchedulePostUpload, Integer> {
}
