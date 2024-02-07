package by.anpoliakov.repository;

import by.anpoliakov.domain.entity.MeterType;

import java.util.List;
import java.util.Optional;

public interface MeterTypeRepository {
    Optional<MeterType> add(String typeName);
    Optional<MeterType> getMeterType(String typeName);
    Optional<List<String>> getNamesMetersTypes();
}
