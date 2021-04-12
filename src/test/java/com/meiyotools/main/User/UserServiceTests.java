package com.meiyotools.main.User;

import com.meiyotools.main.model.entity.User;
import com.meiyotools.main.model.repository.UserRepository;
import com.meiyotools.main.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository repository;
    private UserService underTest;

    @BeforeEach
    public void setup() {
        underTest = new UserService(repository);
    }

    @Test
    void itShouldAddAnUser() {
        //given
        User user = new User("Test", "test", null);
        //when
        //then
    }
}
