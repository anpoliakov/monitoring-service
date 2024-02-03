package by.anpoliakov.repository.impl;

import by.anpoliakov.domain.entity.MeterType;
import by.anpoliakov.repository.MeterTypeRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Класс для проверки/регистрации новых типов счетчиков */
public class MeterTypeRepositoryImpl implements MeterTypeRepository {
    private static MeterTypeRepository instance;
    private static Map<String, MeterType> metersTypes;

    public MeterTypeRepositoryImpl() {
        metersTypes = new HashMap<>();
        installInitialData();
    }

    public static MeterTypeRepository getInstance(){
        if (instance == null){
            instance = new MeterTypeRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void add(String typeName) {
        MeterType meterType = new MeterType(typeName);
        metersTypes.put(typeName, meterType);
    }

    @Override
    public Optional<MeterType> getMeterType(String typeName) {
        return Optional.ofNullable(metersTypes.get(typeName));
    }

    @Override
    public Optional<List<String>> getNamesMetersTypes(){
        return Optional.ofNullable(metersTypes.keySet().stream().toList());
    }

    private void installInitialData(){
        metersTypes.put("Cold water meter", new MeterType("Cold water meter"));
        metersTypes.put("Hot water meter", new MeterType("Hot water meter"));
        metersTypes.put("Heating meter", new MeterType("Heating meter"));
    }
}
