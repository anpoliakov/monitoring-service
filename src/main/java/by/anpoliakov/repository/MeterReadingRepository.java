package by.anpoliakov.repository;

import by.anpoliakov.domain.entity.MeterReading;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface MeterReadingRepository {
    void add(MeterReading meterReading);
    Optional <List<MeterReading>> getLastMetersReadingsByUserId(BigInteger userId);
    boolean hasMeterReading(MeterReading meterReading);
    Optional <List<MeterReading>> getMetersReadingsBySpecificDate(BigInteger userId, int month, int year);
    Optional <List<MeterReading>> getMetersReadingsUser(BigInteger userId);

}
