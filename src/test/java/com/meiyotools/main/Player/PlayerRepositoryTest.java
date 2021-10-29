package com.meiyotools.main.Player;

import com.meiyotools.main.model.entity.Player;
import com.meiyotools.main.model.repository.PlayerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository underTest;
    private Player sample;

    @BeforeEach
    void setup() {
        sample = new Player("playername", "warrior", "tank", null, true);
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldReturnAPlayerFromName() {
        //given
        String playerName = sample.getName();
        underTest.save(sample);
        //when
        Optional<Player> expected = underTest.findByName(playerName);
        //then
        assertThat(expected.get()).isEqualTo(sample);
    }

    @Test
    void itShouldReturnAListOfPlayerFromClassName() {
        //given
        String classname = "warrior";
        underTest.save(sample);
        Player sample2 = new Player("test", "warrior", "dps", null, true);
        underTest.save(sample2);
        List<Player> list = new ArrayList<Player>();
        list.add(sample);
        list.add(sample2);
        //when
        Optional<List<Player>> expected = underTest.findByClassName(classname);
        //then
        assertThat(expected.get().size()).isEqualTo(list.size());
    }

    @Test
    void itShouldReturnAListOfPlayerFromRole() {
        //given
        String role = "tank";
        underTest.save(sample);
        Player sample2 = new Player("test", "warrior", "tank", null, true);
        underTest.save(sample2);
        List<Player> list = new ArrayList<Player>();
        list.add(sample);
        list.add(sample2);
        //when
        Optional<List<Player>> expected = underTest.findByRole(role);
        //then
        assertThat(expected.get().size()).isEqualTo(list.size());
    }
}
