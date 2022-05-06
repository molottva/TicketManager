package ru.netology.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import ru.netology.data.TicketData;
import ru.netology.exception.AlreadyExistsException;
import ru.netology.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class TicketRepositoryTest {
    TicketRepository repositoryEmpty = new TicketRepository();
    TicketRepository repositoryWithOneTicket = new TicketRepository();
    TicketRepository repositoryWithTenTicket = new TicketRepository();

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

    @BeforeEach
    void setup() {
        repositoryWithOneTicket.addTicket(ticketOne);
        repositoryWithTenTicket.addTicket(ticketOne);
        repositoryWithTenTicket.addTicket(ticketTwo);
        repositoryWithTenTicket.addTicket(ticketThree);
        repositoryWithTenTicket.addTicket(ticketFour);
        repositoryWithTenTicket.addTicket(ticketFive);
        repositoryWithTenTicket.addTicket(ticketSix);
        repositoryWithTenTicket.addTicket(ticketSeven);
        repositoryWithTenTicket.addTicket(ticketEight);
        repositoryWithTenTicket.addTicket(ticketNine);
        repositoryWithTenTicket.addTicket(ticketTen);
    }

    @Test
    void shouldAddTicket() {
        TicketData[] expected = new TicketData[]{
                ticketOne,
                ticketTwo,
                ticketThree};
        repositoryEmpty.addTicket(ticketOne);
        repositoryEmpty.addTicket(ticketTwo);
        repositoryEmpty.addTicket(ticketThree);
        assertArrayEquals(expected, repositoryEmpty.findAll());
    }

    @Test
    void shouldAddTicketException() {
        assertThrows(AlreadyExistsException.class, () -> {
            repositoryWithTenTicket.addTicket(ticketOne);
        });
    }

    @Test
    void shouldRemoveById() {
        TicketData[] expected = new TicketData[]{
                ticketOne,
                ticketTwo,
                ticketThree,
                ticketFour,
                ticketFive,
                ticketNine,
                ticketTen};
        repositoryWithTenTicket.removeById(6);
        repositoryWithTenTicket.removeById(7);
        repositoryWithTenTicket.removeById(8);
        assertArrayEquals(expected, repositoryWithTenTicket.findAll());
    }

    @Test
    void shouldRemoveByIdException() {
        assertThrows(NotFoundException.class, () -> {
            repositoryWithTenTicket.removeById(11);
        });
    }

    @Test
    void shouldFindByIdPass() {
        assertEquals(ticketEight, repositoryWithTenTicket.findById(8));
    }

    @Test
    void shouldFindByIdNull() {
        assertNull(repositoryWithTenTicket.findById(12));
    }
}