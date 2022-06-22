package com.triple.mileage.repository;

import com.triple.mileage.domain.Image;
import com.triple.mileage.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Image i where i.review.id = :reviewId")
    void deleteAllBy(UUID reviewId);

    List<Image> findAllByReview(Review review);


}
