package ru.netology.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TicketData {
    private int id;
    private int price;
    private String from;
    private String to;
    private int duration;
}
