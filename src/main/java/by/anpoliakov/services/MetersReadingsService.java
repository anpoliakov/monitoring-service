package by.anpoliakov.services;

import by.anpoliakov.domain.entities.MetersReadings;
import by.anpoliakov.domain.entities.MeterType;
import by.anpoliakov.domain.entities.User;

import java.time.LocalDate;
import java.util.*;

/** Сервис по работе с показаниями счётчиков */
public class MetersReadingsService {
    private Map<User, List<MetersReadings>> userReadings;
    private MeterTypeRegistry meterTypeRegistry;

    public MetersReadingsService(MeterTypeRegistry meterTypeRegistry) {
        userReadings = new HashMap<>();
        this.meterTypeRegistry = meterTypeRegistry;
    }

    /** Метод добавления показаний */
    public void addReading(User user, String typeName, Integer value, LocalDate date) {
        MeterType meterType = meterTypeRegistry.getMeterType(typeName);
        if(meterType == null) {
            System.out.println("Type of meter not recognized. Please register the type before adding readings.");
        }else {
            MetersReadings metersReadings = getMetersReadingsByUserAndDate(user, date).get();
            Map<MeterType, Integer> readings = metersReadings.getReadings();
            readings.put(meterTypeRegistry.getMeterType(typeName), value);
        }
    }

    /** Получение последних показаний счётчиков */
    public MetersReadings getLastMeterReadingsUser(User user){
        MetersReadings lastMetersReadings = null;

        if(hasUserReadings(user)){
            List<MetersReadings> listMetersReadings = userReadings.get(user);
            lastMetersReadings = listMetersReadings.get(listMetersReadings.size() - 1);
        }

        return lastMetersReadings;
    }

    /** Проверка, добавлял ли ранее пользователь значения счётчиков в принципе */
    public boolean hasUserReadings(User user){
        boolean hasSubmitted = false;

        List<MetersReadings> listMetersReadings = userReadings.get(user);
        if(listMetersReadings != null){
            hasSubmitted = true;
        }

        return hasSubmitted;
    }

    /** Проверка, добавлял ли пользователь значение счётчиков за определённый месяц */
    public boolean hasUserReadingsByDate(User user, LocalDate localDate){
        boolean hasReadings = false;
        List<MetersReadings> listMetersReadings = userReadings.get(user);

        if(hasUserReadings(user)){
            hasReadings = listMetersReadings.stream().
                    map(MetersReadings::getDate).
                    anyMatch(date -> date.getMonth().equals(localDate.getMonth()));
        }

        return hasReadings;
    }

    /** Получить все показания счётчиков пользователя за определённый месяц */
    public Optional<MetersReadings> getMetersReadingsByUserAndDate(User user, LocalDate date){
        return userReadings.get(user).stream().
                filter(reading -> reading.getDate().getMonth().equals(date.getMonth())).findFirst();
    }
}
