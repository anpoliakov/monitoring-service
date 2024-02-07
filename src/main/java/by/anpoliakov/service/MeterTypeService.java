package by.anpoliakov.service;

import by.anpoliakov.domain.entity.MeterType;

import java.util.List;

public interface MeterTypeService {
    MeterType addMeterType(String typeName);

    MeterType getMeterType(String typeName);

    List<String> getNamesMetersTypes();
}
