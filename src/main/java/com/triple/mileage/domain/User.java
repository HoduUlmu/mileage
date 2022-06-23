package com.triple.mileage.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Timestamped {

    @Id
    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private UUID id;

    private String email;

    private Long points;

    @Builder
    public User(@NonNull UUID userId, @NonNull String email) {
        this.id = userId;
        this.email = email;
        this.points = 0L;
    }

    public void changePoint(Long point) {
        this.points += point;
    }
}
