package ru.netology.manager;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.netology.data.TicketData;
import ru.netology.exception.NotFoundException;
import ru.netology.repository.TicketRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

class TicketManagerTest {
    @Mock
    TicketRepository repository = Mockito.mock(TicketRepository.class);
    @InjectMocks
    TicketManager manager = new TicketManager(repository);

    // тестовые данные
    TicketData ticketOne = new TicketData(1, 25_068, "DME", "LED", 95);
    TicketData ticketTwo = new TicketData(2, 17_938, "SVO", "LED", 90);
    TicketData ticketThree = new TicketData(3, 14_871, "VKO", "RVH", 80);
    TicketData ticketFour = new TicketData(4, 7_849, "SVO", "LED", 95);
    TicketData ticketFive = new TicketData(5, 11_496, "SVO", "LED", 85);
    TicketData ticketSix = new TicketData(6, 13_379, "VKO", "LED", 80);
    TicketData ticketSeven = new TicketData(7, 10_680, "SVO", "RVH", 90);
    TicketData ticketEight = new TicketData(8, 15_950, "SVO", "LED", 90);
    TicketData ticketNine = new TicketData(9, 12_760, "VKO", "LED", 80);
    TicketData ticketTen = new TicketData(10, 10_680, "SVO", "RVH", 80);

    //заглушки репо
    TicketData[] mockEmpty = new TicketData[0];
    TicketData[] mockOneTicket = new TicketData[]{ticketOne};
    TicketData[] mockTenTicket = new TicketData[]{
            ticketOne,
            ticketTwo,
            ticketThree,
            ticketFour,
            ticketFive,
            ticketSix,
            ticketSeven,
            ticketEight,
            ticketNine,
            ticketTen};

    //тесты на исключение NotFoundException в findAll()
    @Test
    void shouldNotFindMockEmpty() {
        doReturn(mockEmpty).when(repository).findAll();
        assertThrows(NotFoundException.class, () -> {
            manager.findAll("DME", "LED");
        });
    }

    @Test
    void shouldNotFindMockWithOneTicket() {
        doReturn(mockOneTicket).when(repository).findAll();
        assertThrows(NotFoundException.class, () -> {
            manager.findAll("DME", "PES");
        });
    }

    @Test
    void shouldNotFindMockWithTenTicket() {
        doReturn(mockTenTicket).when(repository).findAll();
        assertThrows(NotFoundException.class, () -> {
            manager.findAll("VKO", "PES");
        });
    }

    //тесты на один результат поиска в findAll()
    @Test
    void shouldFindOneResultMockWithOneTicket() {
        doReturn(mockOneTicket).when(repository).findAll();
        TicketData[] expected = new TicketData[]{ticketOne};
        assertArrayEquals(expected, manager.findAll("dme", "led"));
    }

    @Test
    void shouldFindOneResultMockWithTenTicket() {
        doReturn(mockTenTicket).when(repository).findAll();
        TicketData[] expected = new TicketData[]{ticketThree};
        assertArrayEquals(expected, manager.findAll("vko", "rvh"));
    }

    //тесты на несколько результатов в findAll()
    @Test
    void shouldFindManyResultsMockWithTenTicketOne() {
        doReturn(mockTenTicket).when(repository).findAll();
        TicketData[] expected = new TicketData[]{
                ticketSeven,
                ticketTen};
        assertArrayEquals(expected, manager.findAll("svo", "rvh"));
    }

    @Test
    void shouldFindManyResultsMockWithTenTicketTwo() {
        doReturn(mockTenTicket).when(repository).findAll();
        TicketData[] expected = new TicketData[]{
                ticketFour,
                ticketFive,
                ticketEight,
                ticketTwo};
        assertArrayEquals(expected, manager.findAll("svo", "led"));
    }

    //тесты на matches()
    @Test
    void shouldMatchesFromToTrue() {
        assertTrue(manager.matchesFromTo(ticketOne, "dme", "led"));
    }

    @Test
    void shouldMatchesFromToFalseOne() {
        assertFalse(manager.matchesFromTo(ticketOne, "SVO", "LED"));
    }

    @Test
    void shouldMatchesFromToFalseTwo() {
        assertFalse(manager.matchesFromTo(ticketOne, "DME", "RVH"));
    }

    @Test
    void shouldMatchesFromToFalseThree() {
        assertFalse(manager.matchesFromTo(ticketOne, "VKO", "RVH"));
    }
}