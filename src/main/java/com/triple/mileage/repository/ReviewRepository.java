package com.triple.mileage.repository;

import com.triple.mileage.domain.Place;
import com.triple.mileage.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    Optional<Review> findDistinctFirstByPlaceOrderByCreatedAt(Place place);
}
