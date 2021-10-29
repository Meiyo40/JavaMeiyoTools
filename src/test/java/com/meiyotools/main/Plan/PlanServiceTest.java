package com.meiyotools.main.Plan;

import com.meiyotools.main.model.entity.Plan;
import com.meiyotools.main.model.repository.PlanRepository;
import com.meiyotools.main.service.PlanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PlanServiceTest {
    @Mock
    private PlanRepository repository;
    private PlanService underTest;
    private Plan sample;

    @BeforeEach
    void setup() {
        underTest = new PlanService(repository);
        sample = new Plan("planName", "raidName", "Lorem ipsum", LocalDate.now());
    }

    @Test
    void itShouldAddAPlan() {
        //given
        //when
        underTest.setPlan(sample);
        //then
        ArgumentCaptor<Plan> argumentCaptor = ArgumentCaptor.forClass(Plan.class);
        verify(repository).save(argumentCaptor.capture());
        Plan expected = argumentCaptor.getValue();

        assertThat(expected).isEqualTo(sample);
    }

    @Test
    void itShouldReturnAPlanFromPlanName() {
        //given
        String planName = sample.getPlanName();
        given(repository.findByPlanName(planName)).willReturn(
                Optional.of(sample)
        );
        //when
        Plan expected = underTest.getPlan(planName);
        //then
        assertThat(expected).isEqualTo(sample);
    }

    @Test
    void itShouldReturnNullFromPlanName() {
        //given
        String planName = "plannonexist";
        given(repository.findByPlanName(planName)).willReturn(Optional.empty());
        //when
        Plan expected = underTest.getPlan(planName);
        //then
        assertThat(expected).isEqualTo(null);
    }

    @Test
    void itShouldReturnAPlanFromRaidNameAndPlanName() {
        //given
        String planName = "planName";
        String raidName = "raidName";
        given(repository.findByRaidNameAndPlanName(raidName, planName)).willReturn(
                Optional.of(sample)
        );
        //when
        Plan expected = underTest.getPlan(raidName, planName);
        //then
        assertThat(expected).isEqualTo(sample);
    }

    @Test
    void itShouldReturnNULLFromRaidNameAndPlanName() {
        //given
        String planName = "planName";
        String raidName = "raidName";
        given(repository.findByRaidNameAndPlanName(raidName, planName)).willReturn(
                Optional.empty()
        );
        //when
        Plan expected = underTest.getPlan(raidName, planName);
        //then
        assertThat(expected).isEqualTo(null);
    }

    @Test
    void itShouldReturnAListOfPlanFromRaidName() {
        //given
        String raidName = sample.getRaidName();
        List<Plan> list = new ArrayList<Plan>();
        list.add(sample);
        list.add(sample);
        given(repository.findAllByRaidNameOrderByPriorityDesc(raidName)).willReturn(
                Optional.of(list)
        );
        //when
        List<Plan> expected = underTest.getRaidPlans(raidName);
        //then
        assertThat(expected).isEqualTo(list);
    }

    @Test
    void itShouldReturnNullFromRaidName() {
        //given
        String raidName = "raidnonexist";
        given(repository.findAllByRaidNameOrderByPriorityDesc(raidName)).willReturn(Optional.empty());
        //when
        List<Plan> expected = underTest.getRaidPlans(raidName);
        //then
        assertThat(expected).isEqualTo(null);
    }
}
