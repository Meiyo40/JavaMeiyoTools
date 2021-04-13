package com.meiyotools.main.Plan;

import com.meiyotools.main.model.entity.Plan;
import com.meiyotools.main.model.repository.PlanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PlanRepositoryTest {
    @Autowired
    private PlanRepository underTest;
    private Plan sample;

    @BeforeEach
    void setup() {
        underTest.deleteAll();
        sample = new Plan("planName", "raidName", "Lorem ipsum", LocalDate.now());
    }

    @Test
    void itShouldReturnAPlanFromItsName() {
        //given
        underTest.save(sample);
        //when
        Optional<Plan> expected = underTest.findByPlanName(sample.getPlanName());
        //then
        assertThat(expected.get()).isEqualTo(sample);
    }

    @Test
    void itShouldReturnAPlanFromRaidNameAndPlanName() {
        //given
        String raidName = sample.getRaidName();
        String planName = sample.getPlanName();
        underTest.save(sample);
        //when
        Optional<Plan> expected = underTest.findByRaidNameAndPlanName(raidName, planName);
        //then
        assertThat(expected.get()).isEqualTo(sample);
    }

    @Test
    void itShouldReturnAListOfPlanFromRaidName() {
        //given
        underTest.save(sample);
        Plan sample2 = new Plan("test", "raidName", "test", LocalDate.now());
        underTest.save(sample2);
        //when
        Optional<List<Plan>> expected = underTest.findAllByRaidName(sample.getRaidName());
        //then
        assertThat(expected.get().size()).isEqualTo(2);
    }

}
