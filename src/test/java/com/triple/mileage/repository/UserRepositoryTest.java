package com.triple.mileage.repository;

import com.triple.mileage.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@ExtendWith(SpringExtension.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void uuid() {
        // given
        User user = User.builder()
                .userId(UUID.randomUUID())
                .email("kim@triple.com")
                .build();

        // when
        User savedUser = userRepository.save(user);
        em.flush();
        em.clear();
        User findUser = userRepository.findById(savedUser.getUserId()).orElseThrow();

        // then
        UUID savedUserId = savedUser.getUserId();
        UUID findUserId = findUser.getUserId();
        log.info("savedUserId={}, findUserId={}", savedUserId, findUserId);
        assertThat(savedUserId).isEqualTo(findUserId);
    }

}