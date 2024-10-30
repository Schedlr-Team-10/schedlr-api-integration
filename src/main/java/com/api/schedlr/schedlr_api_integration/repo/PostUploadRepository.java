package com.api.schedlr.schedlr_api_integration.repo;
import com.api.schedlr.schedlr_api_integration.entity.PostUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostUploadRepository extends JpaRepository<PostUpload, Integer> {
    List<PostUpload> findByUserId(Integer userId);
}