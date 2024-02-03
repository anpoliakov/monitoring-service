package by.anpoliakov.service.impl;

import by.anpoliakov.domain.entity.MeterType;
import by.anpoliakov.domain.entity.MetersReadings;
import by.anpoliakov.exception.MeterReadingException;
import by.anpoliakov.exception.MeterTypeException;
import by.anpoliakov.repository.MeterReadingRepository;
import by.anpoliakov.service.MeterReadingService;
import by.anpoliakov.service.MeterTypeService;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class MeterReadingServiceImpl implements MeterReadingService {
    private MeterReadingRepository repoMeterReading;
    private MeterTypeService meterTypeService;

    /**
     * Метод добавления показания счётчика
     *
     * @param loginUser - логин пользователя, который добавляет показания
     * @param typeName - название счётчика
     * @param reading - показания счётчика
     * */
    @Override
    public void addReading(String loginUser, String typeName, Integer reading) throws MeterReadingException, MeterTypeException {
        if(loginUser == null || typeName == null || reading == null ||
                loginUser.trim().isEmpty() || typeName.trim().isEmpty()){
            throw new MeterReadingException("Incorrect meter reading information!");
        }

        Optional<List<MetersReadings>> optionalMetersReadingsUser = repoMeterReading.getMetersReadingsByLogin(loginUser);
        MeterType meterType = meterTypeService.getMeterType(typeName);
        LocalDate dateNow = LocalDate.now();

        //ПОЛЬЗОВАТЕЛЬ ПЕРВЫЙ РАЗ ПОДАЁТ ПОКАЗАНИЯ
        //создаём list<MetersReadings> пользователя
        //создаём первое значение счётчика в MetersReadings в виде Map<MeterType, Integer>
        if(optionalMetersReadingsUser.isEmpty()){
            Map <MeterType, Integer> newMapReadings = new HashMap<>();
            newMapReadings.put(meterType, reading);

            MetersReadings metersReadings = new MetersReadings(loginUser, newMapReadings);
            repoMeterReading.add(metersReadings);

        }else {
            //У ПОЛЬЗОВАТЕЛЯ УЖЕ ИМЕЮТСЯ ПОКАЗАНИЯ
            List<MetersReadings> metersReadingsUser = optionalMetersReadingsUser.get();

            //показания вносятся повторно за этот месяц в году?
            Optional<MetersReadings> optionalMetersReadingsCurrentDate = metersReadingsUser.stream()
                    .filter(date -> date.getDate().getMonthValue() == dateNow.getMonthValue() && date.getDate().getYear() == dateNow.getYear())
                    .findFirst();

            //если да
            if(optionalMetersReadingsCurrentDate.isPresent()){
                //проверяем вносил ли показания этого MeterType
                MetersReadings metersReadings = optionalMetersReadingsCurrentDate.get();
                Map<MeterType, Integer> readings = metersReadings.getReadings();

                if(readings.containsKey(meterType)){
                    throw new MeterReadingException("Meter reading [" + typeName + "] for month " + dateNow.getMonth().name() + " already entered");
                }else {
                    readings.put(meterType, reading);
                }

            }else {
                //если нет - показания в этом месяце года не вносились ещё
                Map <MeterType, Integer> readings = new HashMap<>();
                readings.put(meterType, reading);

                metersReadingsUser.add(new MetersReadings(loginUser, readings));
            }
        }

    }

    @Override
    /** Получение последних показаний счётчиков */
    public MetersReadings getLastMetersReadingsUser(String loginUser) throws MeterReadingException {
        if(loginUser.trim().isEmpty() || loginUser == null){
            throw new MeterReadingException("Incorrect data [loginUser]");
        }

        Optional<List<MetersReadings>> metersReadingsByLogin = repoMeterReading.getMetersReadingsByLogin(loginUser);
        if(metersReadingsByLogin.isEmpty()){
            throw new MeterReadingException("Information about meters readings doesn't exist yet!");
        }

        List<MetersReadings> metersReadingsUser = metersReadingsByLogin.get();
        return metersReadingsUser.stream().reduce((first, second) -> second).get();
    }

    @Override
    public List<MetersReadings> getMetersReadingsByUser(String loginUser) throws MeterReadingException {
        Optional<List<MetersReadings>> metersReadingsByLogin = repoMeterReading.getMetersReadingsByLogin(loginUser);
        if(metersReadingsByLogin.isEmpty()){
            throw new MeterReadingException("Information about meters readings doesn't exist yet!");
        }

        return metersReadingsByLogin.get();
    }

    @Override
    public MetersReadings getMetersReadingsBySpecificMonth(String loginUser, int month, int year) throws MeterReadingException {
        if(month < 1 || month > 12){
            throw new MeterReadingException("Month should be between 1 and 12!");
        }

        if(year < 2024){
            throw new MeterReadingException("Year should be more 2024");
        }

        Optional<List<MetersReadings>> metersReadingsByLogin = repoMeterReading.getMetersReadingsByLogin(loginUser);
        if(metersReadingsByLogin.isEmpty()){
            throw new MeterReadingException("Information about meters readings doesn't exist yet!");
        }

        List<MetersReadings> metersReadings = metersReadingsByLogin.get();
        Optional<MetersReadings> optionalMetersReadings = metersReadings.stream()
                .filter(date -> date.getDate().getMonthValue() == month && date.getDate().getYear() == year).findFirst();

        if(optionalMetersReadings.isEmpty()){
            throw new MeterReadingException("No readings were submitted in the " + month + "th month of " + year);
        }

        return optionalMetersReadings.get();
    }

}
