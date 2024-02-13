package by.anpoliakov.service.impl;

import by.anpoliakov.domain.entity.MeterReading;
import by.anpoliakov.domain.entity.MeterType;
import by.anpoliakov.exception.MeterReadingException;
import by.anpoliakov.exception.MeterTypeException;
import by.anpoliakov.repository.MeterReadingRepository;
import by.anpoliakov.service.MeterReadingService;
import by.anpoliakov.service.MeterTypeService;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class MeterReadingServiceImpl implements MeterReadingService {
    private MeterReadingRepository repoMeterReading;
    private MeterTypeService meterTypeService;

    public MeterReadingServiceImpl(MeterReadingRepository repoMeterReading, MeterTypeService meterTypeService) {
        this.repoMeterReading = repoMeterReading;
        this.meterTypeService = meterTypeService;
    }

    @Override
    public void addReading(BigInteger userId, String nameTypeMeter, Integer reading) throws MeterReadingException, MeterTypeException {
        if (userId == null || nameTypeMeter == null || reading == null) {
            throw new MeterReadingException("Parameters should be not null!");
        }

        MeterType meterType = meterTypeService.getMeterType(nameTypeMeter);
        MeterReading meterReading = new MeterReading(userId, meterType.getId(), reading, LocalDateTime.now());
        boolean hasMeterReading = repoMeterReading.hasMeterReading(meterReading);

        if (hasMeterReading) {
            throw new MeterReadingException("The current month's meter readings have already been entered!");
        }

        repoMeterReading.create(meterReading);
    }

    @Override
    public List<MeterReading> getLastMetersReadingsByUserId(BigInteger userId) throws MeterReadingException {
        if (userId == null) {
            throw new MeterReadingException("Incorrect id's user!");
        }

        Optional<List<MeterReading>> metersReadings = repoMeterReading.findLastMetersReadingsByUserId(userId);
        if (metersReadings.isEmpty()) {
            throw new MeterReadingException("The user's meter readings do not yet exist!");
        }

        return metersReadings.get();
    }

    @Override
    public List<MeterReading> getMetersReadingsBySpecificDate(BigInteger userId, int month, int year) {
        if (month < 0 || month > 12 || year < 2024) {
            throw new MeterReadingException("Incorrect data: month or(and) year");
        }

        Optional<List<MeterReading>> metersReadings = repoMeterReading.findMetersReadingsBySpecificDate(userId, month, year);
        if (metersReadings.isEmpty()) {
            throw new MeterReadingException("In this " + month + "th month of " + year + ", the user did not submit a meter reading");
        }

        return metersReadings.get();
    }

    @Override
    public List<MeterReading> getAllMetersReadingsUser(BigInteger userId) {
        Optional<List<MeterReading>> metersReadings = repoMeterReading.findMetersReadingsByUserId(userId);
        if (metersReadings.isEmpty()) {
            throw new MeterReadingException("The user's meter readings do not yet exist!");
        }

        return metersReadings.get();
    }
}
