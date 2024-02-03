package by.anpoliakov.repository;

import by.anpoliakov.domain.entity.MetersReadings;

import java.util.List;
import java.util.Optional;

public interface MeterReadingRepository {
    void add(MetersReadings metersReadings);
    Optional <List<MetersReadings>> getMetersReadingsByLogin(String login);
}
