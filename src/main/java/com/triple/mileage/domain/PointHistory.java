package com.triple.mileage.domain;

import com.triple.mileage.constant.ActionEnum;
import com.triple.mileage.constant.TypeEnum;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private UUID eventId;

    @Enumerated(EnumType.STRING)
    private TypeEnum eventType;

    @Enumerated(EnumType.STRING)
    private ActionEnum eventAction;

    private Long pointChange;

    @Builder
    public PointHistory(User user, UUID eventId, TypeEnum eventType, ActionEnum eventAction, Long pointChange) {
        this.user = user;
        this.eventId = eventId;
        this.eventType = eventType;
        this.eventAction = eventAction;
        this.pointChange = pointChange;
    }
}
