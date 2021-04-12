package com.meiyotools.main.User;

import com.meiyotools.main.model.entity.User;
import com.meiyotools.main.model.repository.UserRepository;
import com.meiyotools.main.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.firewall.RequestRejectedException;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    private UserRepository repository;
    private UserService underTest;

    @BeforeEach
    void setup() {
        underTest = new UserService(repository, new BCryptPasswordEncoder());
    }

    @Test
    void itShouldAddAnUser() {
        //given
        User user = new User("Test", "test@test.fr", "test", "ROLE_ADMIN", LocalDate.now());
        //when
        underTest.registerNewUser(user);
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(repository).save(userArgumentCaptor.capture());
        User captedUser = userArgumentCaptor.getValue();

        assertThat(captedUser).isEqualTo(user);
    }

    @Test
    void itShouldFailToAddUserBecauseNameAlreadyExist() {
        //given
        User user = new User("Test", "test@test.fr", "test", "ROLE_ADMIN", LocalDate.now());
        //when
        given(repository.findByUsername(user.getUsername())).willReturn(
                Optional.of(user)
        );
        //then
        assertThatThrownBy( () -> underTest.registerNewUser(user))
                .isInstanceOf(RequestRejectedException.class)
                .hasMessage("User already exist.");
    }
}
