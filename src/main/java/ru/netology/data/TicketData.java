package ru.netology.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TicketData implements Comparable<TicketData> {
    private int id;
    private int price;
    private String from;
    private String to;
    private int duration;

    @Override
    public int compareTo(TicketData o) {
        return price - o.getPrice();
    }
}
