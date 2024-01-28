package by.anpoliakov.services;

import by.anpoliakov.domain.entities.MeterReading;
import by.anpoliakov.domain.entities.MeterType;
import by.anpoliakov.domain.entities.User;
import by.anpoliakov.repository.impl.MeterTypeRegistry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeterReadingService {
    //Ключ - конкретный User, Значение - список в порядке добавления
    private Map<User, List<MeterReading>> userReadings;
    private MeterTypeRegistry meterTypeRegistry;

    public MeterReadingService(MeterTypeRegistry meterTypeRegistry) {
        userReadings = new HashMap<>();
        this.meterTypeRegistry = meterTypeRegistry;
    }

    /** Метод добавления показаний */
    public void addReading(User user, String typeName, Integer value, LocalDate date) {
        MeterType meterType = meterTypeRegistry.getMeterType(typeName);
        if(meterType == null) {
            System.out.println("Type of meter not recognized. Please register the type before adding readings.");
        }else {
            // Логика добавления показаний для данного meterType
            // проверка в текущнм месяце клиент вносил показания?

        }
    }

    public Map<MeterType, Integer> getLastReadingsByUser(User user){
        List<MeterReading> readingsList = userReadings.get(user);
        if(readingsList != null){
            MeterReading meterReading = readingsList.get(readingsList.size() - 1);
            return meterReading.getReadings();
        }
        return null;
    }

    /** Получение последних показаний счётчиков */
    public MeterReading getLastMeterReadingsForUser(User user){
        MeterReading lastMeterReading = null;

        if(hasUserSubmittedReadings(user)){
            List<MeterReading> meterReadings = userReadings.get(user);
            lastMeterReading = meterReadings.get(meterReadings.size() - 1);
        }

        return lastMeterReading;
    }

    /** Проверка, добавлял ли ранее пользователь значения счётчиков */
    public boolean hasUserSubmittedReadings(User user){
        boolean hasSubmitted = false;

        List<MeterReading> meterReadings = userReadings.get(user);
        if(meterReadings != null){
            hasSubmitted = true;
        }

        return hasSubmitted;
    }
}
