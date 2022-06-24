package com.triple.mileage.service;

import com.triple.mileage.domain.PointHistory;
import com.triple.mileage.domain.User;
import com.triple.mileage.exception.custom.business.UserNotFoundException;
import com.triple.mileage.repository.PointHistoryRepository;
import com.triple.mileage.repository.UserRepository;
import com.triple.mileage.constant.ActionEnum;
import com.triple.mileage.constant.TypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PointService {

    private final UserRepository userRepository;
    private final PointHistoryRepository historyRepository;

    @Transactional(readOnly = true)
    public Long getUserPoint(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return user.getPoints();
    }


    @Transactional
    public void changeUserPoint(User user, UUID eventId, Long addPoint, TypeEnum type, ActionEnum action) {
        if (addPoint != 0) {
            user.changePoint(addPoint);
            PointHistory history = PointHistory.builder()
                    .user(user)
                    .pointChange(addPoint)
                    .eventId(eventId)
                    .eventType(type)
                    .eventAction(action)
                    .build();
            historyRepository.save(history);
        }
    }
}
