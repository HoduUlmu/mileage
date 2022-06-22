package com.triple.mileage.repository;

import com.triple.mileage.domain.Place;
import com.triple.mileage.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    Optional<Review> findDistinctFirstByPlaceOrderByCreatedAt(Place place);

    @Query("select r from Review r " +
            "where r.id = :reviewId and r.user.id = :userId and r.place.id = :placeId")
    Optional<Review> findReviewBy(@Param("reviewId") UUID reviewId,
                          @Param("userId") UUID userId,
                          @Param("placeId") UUID placeId);

}
