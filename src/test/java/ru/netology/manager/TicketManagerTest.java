package ru.netology.manager;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.netology.data.TicketData;
import ru.netology.exception.NotFoundException;
import ru.netology.repository.TicketRepository;
import ru.netology.сomparator.TicketByDurationAscComparator;
import ru.netology.сomparator.TicketByPriceAscComparator;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

class TicketManagerTest {
    @Mock
    TicketRepository repository = Mockito.mock(TicketRepository.class);
    @InjectMocks
    TicketManager manager = new TicketManager(repository);

    //компараторы
    Comparator<TicketData> comparatorByDuration = new TicketByDurationAscComparator();
    Comparator<TicketData> comparatorByPrice = new TicketByPriceAscComparator();

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
            manager.findAll("DME", "LED", comparatorByPrice);
        });
    }

    @Test
    void shouldNotFindMockWithOneTicket() {
        doReturn(mockOneTicket).when(repository).findAll();
        assertThrows(NotFoundException.class, () -> {
            manager.findAll("DME", "PES", comparatorByDuration);
        });
    }

    @Test
    void shouldNotFindMockWithTenTicket() {
        doReturn(mockTenTicket).when(repository).findAll();
        assertThrows(NotFoundException.class, () -> {
            manager.findAll("VKO", "PES", comparatorByDuration);
        });
    }

    //тесты на один результат поиска в findAll()
    @Test
    void shouldFindOneResultMockWithOneTicket() {
        doReturn(mockOneTicket).when(repository).findAll();
        TicketData[] expected = new TicketData[]{ticketOne};
        assertArrayEquals(expected, manager.findAll("dme", "led", comparatorByPrice));
    }

    @Test
    void shouldFindOneResultMockWithTenTicket() {
        doReturn(mockTenTicket).when(repository).findAll();
        TicketData[] expected = new TicketData[]{ticketThree};
        assertArrayEquals(expected, manager.findAll("vko", "rvh", comparatorByPrice));
    }

    //тесты на несколько результатов в findAll() c сортировкой по цене
    @Test
    void shouldFindManyResultsMockWithTenTicketSortByPriceOne() {
        doReturn(mockTenTicket).when(repository).findAll();
        TicketData[] expected = new TicketData[]{
                ticketSeven,
                ticketTen};
        assertArrayEquals(expected, manager.findAll("svo", "rvh", comparatorByPrice));
    }

    @Test
    void shouldFindManyResultsMockWithTenTicketSortByPriceTwo() {
        doReturn(mockTenTicket).when(repository).findAll();
        TicketData[] expected = new TicketData[]{
                ticketFour,
                ticketFive,
                ticketEight,
                ticketTwo};
        assertArrayEquals(expected, manager.findAll("svo", "led", comparatorByPrice));
    }

    //тесты на несколько результатов в findAll() c сортировкой по времени полета
    @Test
    void shouldFindManyResultsMockWithTenTicketSortByDurationOne() {
        doReturn(mockTenTicket).when(repository).findAll();
        TicketData[] expected = new TicketData[]{
                ticketTen,
                ticketSeven};
        assertArrayEquals(expected, manager.findAll("svo", "rvh", comparatorByDuration));
    }

    @Test
    void shouldFindManyResultsMockWithTenTicketSortByDurationTwo() {
        doReturn(mockTenTicket).when(repository).findAll();
        TicketData[] expected = new TicketData[]{
                ticketFive,
                ticketTwo,
                ticketEight,
                ticketFour};
        assertArrayEquals(expected, manager.findAll("svo", "led", comparatorByDuration));
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