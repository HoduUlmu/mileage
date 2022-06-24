package com.triple.mileage.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends Timestamped {

    @Id
    @Column(name = "review_id", columnDefinition = "BINARY(16)")
    private UUID id;

    private String content;

    private Long givenPoint;

    private boolean isFirst;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Review(UUID reviewId, String content, Long givenPoint, Place place, User user, boolean isFirst) {
        this.id = reviewId;
        this.content = content;
        this.givenPoint = givenPoint;
        this.place = place;
        this.user = user;
        this.isFirst = isFirst;
    }

    public void change(String content, Long changePoint) {
        this.content = content;
        this.givenPoint = changePoint;
    }
}
