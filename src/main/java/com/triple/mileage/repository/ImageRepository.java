package com.triple.mileage.repository;

import com.triple.mileage.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Image i where i.review.reviewId = :reviewId")
    void deleteAllBy(UUID reviewId);
}
