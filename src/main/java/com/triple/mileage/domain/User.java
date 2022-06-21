package com.triple.mileage.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private UUID userId;

    private String email;

    private Long point;

    @Builder
    public User(UUID userId, String email) {
        this.userId = userId;
        this.email = email;
        this.point = 0L;
    }

    public void addReviewPoint(Long point) {
        this.point += point;
    }
}
