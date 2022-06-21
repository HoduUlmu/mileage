package com.triple.mileage.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @Column(name = "review_id", columnDefinition = "BINARY(16)")
    private UUID reviewId;

    private String content;

    @OneToMany(mappedBy = "review", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Image> images = new HashSet<>();

    private Long givenPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Review(UUID reviewId, String content, Long givenPoint, Place place, User user) {
        this.reviewId = reviewId;
        this.content = content;
        this.givenPoint = givenPoint;
        this.place = place;
        this.user = user;
    }

    public void change(String content, Set<UUID> attachedPhotoIds, Long changePoint) {
        this.content = content;
        this.images.removeIf((image) -> !attachedPhotoIds.contains(image.getImageId()));
        Set<Image> addImages = attachedPhotoIds.stream()
                .map((imageId) -> new Image(imageId, this))
                .collect(Collectors.toSet());
        this.images.addAll(addImages);
        this.givenPoint = changePoint;
    }
}
