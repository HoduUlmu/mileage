package com.triple.mileage.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place extends Timestamped {
    @Id
    @Column(name = "place_id", columnDefinition = "BINARY(16)")
    private UUID placeId;
}
