package by.anpoliakov.service;

import by.anpoliakov.domain.entity.MeterType;

import java.util.List;

public interface MeterTypeService {
    void addMeterType(String typeName);
    MeterType getMeterType(String typeName);
    List<String> getNamesMetersTypes();
}
