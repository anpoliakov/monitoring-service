package by.anpoliakov.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Map;

@Setter
@Getter
@ToString
/** Класс представляет собой показания счётчиков за определённую дату */
public class MetersReadings {
    private String loginUser;
    private Map<MeterType, Integer> readings;
    private LocalDateTime date;

    public MetersReadings(String loginUser, Map<MeterType, Integer> readings) {
        this.loginUser = loginUser;
        this.readings = readings;
        this.date = LocalDateTime.now();
    }
}
