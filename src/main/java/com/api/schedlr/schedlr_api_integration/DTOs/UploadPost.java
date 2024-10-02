package com.api.schedlr.schedlr_api_integration.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadPost {

    private int postid;
    private MultipartFile image;
    private String description;
    private int userId;
    private boolean instagram;
    private boolean facebook;
    private boolean linkedin;
    private boolean twitter;
    private LocalDateTime schedule;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UploadPost that = (UploadPost) o;
        return postid == that.postid && userId == that.userId && instagram == that.instagram && facebook == that.facebook && linkedin == that.linkedin && twitter == that.twitter && Objects.equals(image, that.image) && Objects.equals(description, that.description) && Objects.equals(schedule, that.schedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postid, image, description, userId, instagram, facebook, linkedin, twitter, schedule);
    }

    @Override
    public String toString() {
        return "UploadPost{" +
                "postid=" + postid +
                ", image=" + image +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                ", instagram=" + instagram +
                ", facebook=" + facebook +
                ", linkedin=" + linkedin +
                ", twitter=" + twitter +
                ", schedule=" + schedule +
                '}';
    }
}