package com.meiyotools.main.History;

import com.meiyotools.main.model.entity.History;
import com.meiyotools.main.model.repository.HistoryRepository;
import com.meiyotools.main.service.HistoryService;
import com.sun.org.apache.xpath.internal.Arg;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class HistoryServiceTest {

    @Mock
    private HistoryRepository repository;

    private HistoryService underTest;

    private History sample;

    @Captor
    ArgumentCaptor<Long> argumentCaptorLong;

    @BeforeEach
    void setup() {
        underTest = new HistoryService(repository);
        sample = new History(555L, "PlayerTest", 19019, "TF", 5, LocalDate.now());
    }

    @Test
    void itShouldAddHistory()
    {
        //given
        //when
        underTest.create(sample);
        //then
        ArgumentCaptor<History> argumentCaptor = ArgumentCaptor.forClass(History.class);
        verify(repository).save(argumentCaptor.capture());
        History expected = argumentCaptor.getValue();

        assertThat(expected).isEqualTo(sample);
    }

    @Test
    void itShouldDeleteHistory()
    {
        //given
        final Long IdToDelete = 555L;

        //when
        underTest.deleteHistory(IdToDelete);

        //then
        verify(repository).deleteById(argumentCaptorLong.capture());
        Long expected = argumentCaptorLong.getValue();

        assertThat(expected).isEqualTo(IdToDelete);
    }

    @Test
    void itShoudlUpdateHistory()
    {
        //given
        History updatedHistory = new History(
                sample.getId(),
                sample.getPlayerName(),
                22000,
                "UpdatedItemname",
                sample.getCost(),
                sample.getCreated_at()
        );

        //when
        underTest.update(updatedHistory);

        //then
        ArgumentCaptor<History> argumentCaptor = ArgumentCaptor.forClass(History.class);
        verify(repository).save(argumentCaptor.capture());
        History expected = argumentCaptor.getValue();

        assertThat(expected).isEqualTo(updatedHistory);
    }

    @Test
    void itShouldReturnAnHistoryFromId()
    {
        //given
        final Long IdToGet = 555L;

        //when
        underTest.getHistory(IdToGet);

        //then
        verify(repository).findById(argumentCaptorLong.capture());
        Long expected = argumentCaptorLong.getValue();

        assertThat(expected).isEqualTo(IdToGet);
    }
}
