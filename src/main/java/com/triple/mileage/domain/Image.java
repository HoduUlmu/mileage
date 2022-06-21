package com.triple.mileage.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends Timestamped {
    @Id
    @Column(name = "image_id", columnDefinition = "BINARY(16)")
    private UUID imageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    public Image(UUID imageId, Review review) {
        this.imageId = imageId;
        this.review = review;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return imageId.equals(image.imageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageId);
    }
}
