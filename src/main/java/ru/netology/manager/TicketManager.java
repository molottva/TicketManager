package ru.netology.manager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.netology.data.TicketData;
import ru.netology.exception.NotFoundException;
import ru.netology.repository.TicketRepository;

import java.util.Arrays;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TicketManager {
    private TicketRepository repository = new TicketRepository();

    //методы репо
    //добавление билета в репо
    public void addTicket(TicketData addTicket) {
        repository.addTicket(addTicket);
    }

    // удаление билета из репозитория по id
    public void removeById(int removeId) {
        repository.removeById(removeId);
    }

    // методы менеджера
    // поиск билетов по аэропортам вылета-прилета
    public TicketData[] findAll(String fromInput, String toInput) {
        TicketData[] results = new TicketData[0];
        for (TicketData ticket : repository.findAll()) {
            if (matchesFromTo(ticket, fromInput, toInput)) {
                TicketData[] tmp = new TicketData[results.length + 1];
                int i = 0;
                System.arraycopy(results, 0, tmp, 0, results.length);
                tmp[results.length] = ticket;
                results = tmp;
            }
        }
        if (results.length == 0) {
            throw new NotFoundException("Билетов из " + fromInput + " в " + toInput + " не найдено!");
        }
        Arrays.sort(results);
        return results;
    }

    // сравнение по аэропортам прилета-вылета
    public boolean matchesFromTo(TicketData ticket, String fromInput, String toInput) {
        boolean matchesFrom = ticket.getFrom().toUpperCase().contains(fromInput.toUpperCase());
        boolean matchesTo = ticket.getTo().toUpperCase().contains(toInput.toUpperCase());
        return matchesFrom && matchesTo;
    }
}
