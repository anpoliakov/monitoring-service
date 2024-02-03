package by.anpoliakov.repository.impl;

import by.anpoliakov.domain.entity.MetersReadings;
import by.anpoliakov.repository.MeterReadingRepository;

import java.util.*;

public class MeterReadingRepositoryImpl implements MeterReadingRepository {
    private static MeterReadingRepository instance;
    private Map<String, List<MetersReadings>> metersReadingsUsers;

    private MeterReadingRepositoryImpl() {
        metersReadingsUsers = new HashMap<>();
    }

    public static MeterReadingRepository getInstance(){
        if (instance == null){
            instance = new MeterReadingRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void add(MetersReadings metersReadings) {
        String login = metersReadings.getLoginUser();

        List<MetersReadings> listMetersReadingsUser = Optional.ofNullable(
                this.metersReadingsUsers.get(login)).orElse(new ArrayList<>());

        listMetersReadingsUser.add(metersReadings);
        metersReadingsUsers.put(login, listMetersReadingsUser);
    }

    @Override
    public Optional<List<MetersReadings>> getMetersReadingsByLogin(String login) {
        List<MetersReadings> metersReadingsUser = metersReadingsUsers.get(login);
        return Optional.ofNullable(metersReadingsUser);
    }
}
