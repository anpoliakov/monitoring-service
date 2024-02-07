package by.anpoliakov.service;

import by.anpoliakov.domain.entity.MeterReading;

import java.math.BigInteger;
import java.util.List;

public interface MeterReadingService {
    void addReading(BigInteger userId, String nameTypeMeter, Integer reading);

    List<MeterReading> getLastMetersReadingsByUserId(BigInteger userId);

    List<MeterReading> getMetersReadingsBySpecificDate(BigInteger userId, int month, int year);

    List<MeterReading> getAllMetersReadingsUser(BigInteger userId);
}
