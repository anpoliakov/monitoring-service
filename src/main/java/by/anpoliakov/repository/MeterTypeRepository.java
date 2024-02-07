package by.anpoliakov.repository;

import by.anpoliakov.domain.entity.MeterType;

import java.util.List;
import java.util.Optional;

public interface MeterTypeRepository {
    Optional<MeterType> create(String typeName);

    Optional<MeterType> findMeterType(String typeName);

    Optional<List<String>> getNamesMetersTypes();
}
