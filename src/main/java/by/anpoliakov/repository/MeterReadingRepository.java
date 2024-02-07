package by.anpoliakov.repository;

import by.anpoliakov.domain.entity.MeterReading;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface MeterReadingRepository {
    void create(MeterReading meterReading);

    Optional<List<MeterReading>> findLastMetersReadingsByUserId(BigInteger userId);

    boolean hasMeterReading(MeterReading meterReading);

    Optional<List<MeterReading>> findMetersReadingsBySpecificDate(BigInteger userId, int month, int year);

    Optional<List<MeterReading>> findMetersReadingsByUserId(BigInteger userId);

}
