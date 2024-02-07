package by.anpoliakov.service.impl;

import by.anpoliakov.domain.entity.MeterType;
import by.anpoliakov.exception.MeterTypeException;
import by.anpoliakov.repository.MeterTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MeterTypeServiceImplTest {
    @InjectMocks
    private MeterTypeServiceImpl meterTypeService;

    @Mock
    private MeterTypeRepository repoMeterType;

    @Test
    @DisplayName("Meter type already exist. Add meter type - throw MeterTypeException")
    void addMeterType_meterTypeAlreadyExist_throwMeterTypeException() {
        MeterType meterType = new MeterType("cold water meter");

        Mockito.when(repoMeterType.findMeterType(anyString()))
                .thenReturn(Optional.ofNullable(meterType));

        assertThrows(MeterTypeException.class, () -> meterTypeService.addMeterType(meterType.getTypeName()));
    }

    @Test
    @DisplayName("Meter type successfully added")
    void addMeterType_meterTypeAbsent_meterTypeShouldAdded() {
        MeterType meterType = new MeterType("cold water meter");

        Mockito.when(repoMeterType.create(anyString()))
                .thenReturn(Optional.ofNullable(meterType));

        MeterType actualMeterType = meterTypeService.addMeterType(meterType.getTypeName());

        Assertions.assertEquals(meterType, actualMeterType);
        Mockito.verify(repoMeterType, Mockito.times(1)).create(meterType.getTypeName());
    }

    @Test
    @DisplayName("Meter type absent. Method for get meter type - throw MeterTypeException")
    void getMeterType_meterTypeAbsent_throwMeterTypeException() {
        Mockito.when(repoMeterType.findMeterType(anyString()))
                .thenReturn(Optional.ofNullable(null));

        assertThrows(MeterTypeException.class, () -> meterTypeService.getMeterType("some name"));
    }

    @Test
    @DisplayName("Method for get meter type - should return object MeterType")
    void getMeterType_shouldReturnMeterType() {
        MeterType meterType = new MeterType("cold water meter");
        Mockito.when(repoMeterType.findMeterType(meterType.getTypeName())).thenReturn(Optional.ofNullable(meterType));

        MeterType actualMeterType = meterTypeService.getMeterType(meterType.getTypeName());
        Assertions.assertEquals(meterType.getTypeName(), actualMeterType.getTypeName());
    }

    @Test
    @DisplayName("Method for get all string names - return list names")
    void getNamesMetersTypes_shouldReturnListNamesOfMeters() {
        List<String> actualList = Arrays.asList("Test name one", "Test name two");
        when(repoMeterType.getNamesMetersTypes()).thenReturn(Optional.ofNullable(actualList));

        List<String> expectedList = meterTypeService.getNamesMetersTypes();
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    @DisplayName("Method for get all string names empty database - throw MeterTypeException")
    void getNamesMetersTypes_metersTypesAbsent_throwMeterTypeException() {
        when(repoMeterType.getNamesMetersTypes()).thenReturn(Optional.ofNullable(null));
        assertThrows(MeterTypeException.class, () -> meterTypeService.getNamesMetersTypes());
    }
}