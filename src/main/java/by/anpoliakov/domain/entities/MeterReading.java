package by.anpoliakov.domain.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

@Setter
@Getter
/** Показания конкретных счётчиков на дату */
public class MeterReading {
    private Map<MeterType, Integer> readings;
    private LocalDate date;
    private User user;


}
