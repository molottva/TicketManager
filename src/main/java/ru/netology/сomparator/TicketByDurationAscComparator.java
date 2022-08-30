package ru.netology.сomparator;

import ru.netology.data.TicketData;

import java.util.Comparator;

public class TicketByDurationAscComparator implements Comparator<TicketData> {
    public int compare(TicketData o1, TicketData o2) {
        return o1.getDuration() - o2.getDuration();
    }
}
