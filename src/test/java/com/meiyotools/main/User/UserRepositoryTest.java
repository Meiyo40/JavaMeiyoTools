package com.meiyotools.main.User;

import com.meiyotools.main.model.entity.User;
import com.meiyotools.main.model.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldReturnAnUserFromUsername() {
        //given
        User user = new User("Test", "test@test.fr", "test");
        underTest.save(user);
        //when
        Optional<User> expected = underTest.findByUsername(user.getUsername());
        //then
        assertThat(expected.get()).isEqualTo(user);
    }
}
