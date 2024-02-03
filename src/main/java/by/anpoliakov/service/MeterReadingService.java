package by.anpoliakov.service;

import by.anpoliakov.domain.entity.MetersReadings;

import java.util.List;

public interface MeterReadingService {
    void addReading(String loginUser, String typeName, Integer reading);
    MetersReadings getLastMetersReadingsUser(String loginUser);
    List<MetersReadings> getMetersReadingsByUser(String loginUser);
    MetersReadings getMetersReadingsBySpecificMonth(String loginUser, int month, int year);
}
