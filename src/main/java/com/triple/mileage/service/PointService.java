package com.triple.mileage.service;

import com.triple.mileage.domain.User;
import com.triple.mileage.exception.custom.business.UserNotFoundException;
import com.triple.mileage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PointService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Long getUserPoint(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return user.getPoint();
    }
}
