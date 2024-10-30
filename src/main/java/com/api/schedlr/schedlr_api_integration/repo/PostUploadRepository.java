package com.api.schedlr.schedlr_api_integration.repo;
import com.api.schedlr.schedlr_api_integration.entity.PostUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostUploadRepository extends JpaRepository<PostUpload, Integer> {
    // Custom query methods (if needed) can be added here
}