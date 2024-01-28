package by.anpoliakov.repository.impl;

import by.anpoliakov.domain.entities.MeterType;
import by.anpoliakov.domain.entities.User;
import by.anpoliakov.domain.enums.Role;

import java.util.HashMap;
import java.util.Map;

/** Класс для управления типами счетчиков */
public class MeterTypeRegistry {
    private static MeterTypeRegistry instance;
    private static Map<String, MeterType> meterTypes;

    public MeterTypeRegistry() {
        meterTypes = new HashMap<>();
        meterTypes.put("Cold water meter", new MeterType("Cold water meter"));
        meterTypes.put("Hot water meter", new MeterType("Hot water meter"));
        meterTypes.put("Heating meter", new MeterType("Heating meter"));
    }

    public static MeterTypeRegistry getInstance(){
        if (instance == null){
            instance = new MeterTypeRegistry();
        }
        return instance;
    }

    public void addMeterType(String typeName) {
        MeterType meterType = new MeterType(typeName);
        meterTypes.put(typeName, meterType);
    }

    public MeterType getMeterType(String typeName) {
        return meterTypes.get(typeName);
    }

    public Map<String, MeterType> getTypesMeters(){
        return meterTypes;
    }
}
