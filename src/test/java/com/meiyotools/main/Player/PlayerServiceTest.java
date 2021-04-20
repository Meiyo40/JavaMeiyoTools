package com.meiyotools.main.Player;

import com.meiyotools.main.model.entity.Player;
import com.meiyotools.main.model.repository.PlayerRepository;
import com.meiyotools.main.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {
    @Mock
    private PlayerRepository repository;
    private PlayerService underTest;
    private Player sample;

    @BeforeEach
    void setup() {
        underTest = new PlayerService(repository);
        sample = new Player("playername", "warrior", "tank", null, true);
    }

    @Test
    void itShouldReturnAPlayerFromName() {
        //given
        String name = sample.getName();
        given(repository.findByName(name)).willReturn(
                Optional.of(sample)
        );
        //when
        Player expected = underTest.getPlayerByName(name);
        //then
        assertThat(expected).isEqualTo(sample);
    }

    @Test
    void itShouldReturnAListOfPlayerFromRole() {
        //given
        String role = sample.getRole();
        List<Player> list = new ArrayList<Player>();
        list.add(sample);
        given(repository.findByRole(role)).willReturn(
            Optional.of(list)
        );
        //when
        List<Player> expected = underTest.getPlayersByRole(role);
        //then
        assertThat(expected.size()).isEqualTo(list.size());
    }

    @Test
    void itShouldReturnAListOfPlayerFromClassName() {
        //given
        String className = sample.getClassName();
        List<Player> list = new ArrayList<Player>();
        list.add(sample);
        given(repository.findByClassName(className)).willReturn(
                Optional.of(list)
        );
        //when
        List<Player> expected = underTest.getPlayersByClass(className);
        //then
        assertThat(expected.size()).isEqualTo(list.size());
    }
}
