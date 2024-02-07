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
    private final MeterType METER_TYPE_OBJECT = new MeterType("cold water meter");

    @InjectMocks
    private MeterTypeServiceImpl meterTypeService;

    @Mock
    private MeterTypeRepository repoMeterType;

    @Test
    @DisplayName("Meter type exist. Add meter type method - throw MeterTypeException")
    void addMeterType_ifMeterTypeExistInDb_throwMeterTypeException() {
        Mockito.when(repoMeterType.getMeterType(anyString()))
                .thenReturn(Optional.ofNullable(METER_TYPE_OBJECT));

        assertThrows(MeterTypeException.class, () -> meterTypeService.addMeterType(METER_TYPE_OBJECT.getTypeName()));
    }

    @Test
    @DisplayName("Meter type was added")
    void addMeterType_ifMeterTypeAbsentInDb_MeterTypeShouldAdded() {
        Mockito.when(repoMeterType.add(anyString()))
                .thenReturn(Optional.ofNullable(METER_TYPE_OBJECT));

        MeterType meterType = meterTypeService.addMeterType(METER_TYPE_OBJECT.getTypeName());
        Assertions.assertEquals(meterType, METER_TYPE_OBJECT);
    }

    @Test
    @DisplayName("Meter type absent. Get meter type method - throw MeterTypeException")
    void getMeterType_ifMeterTypeAbsent_throwMeterTypeException() {
        Mockito.when(repoMeterType.getMeterType(anyString()))
                .thenReturn(Optional.ofNullable(null));

        assertThrows(MeterTypeException.class, () -> meterTypeService.getMeterType("some name"));
    }

    @Test
    @DisplayName("Get meter type method - should return object MeterType")
    void getMeterType_shouldReturnMeterType() {
        String typeName = METER_TYPE_OBJECT.getTypeName();
        Mockito.when(repoMeterType.getMeterType(typeName)).thenReturn(Optional.ofNullable(METER_TYPE_OBJECT));

        MeterType meterType = meterTypeService.getMeterType(typeName);
        Assertions.assertEquals(meterType.getTypeName(), METER_TYPE_OBJECT.getTypeName());
    }

    @Test
    @DisplayName("Method for getting all string names - return list")
    void getNamesMetersTypes_shouldReturnListNamesOfMeter() {
        List<String> actualList = Arrays.asList("Test name one", "Test name two");
        when(repoMeterType.getNamesMetersTypes()).thenReturn(Optional.of(actualList));

        List<String> expectedList = meterTypeService.getNamesMetersTypes();
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    @DisplayName("Method for getting all string names - throw MeterTypeException")
    void getNamesMetersTypes_ifAbsentMetersTypes_throwMeterTypeException() {
        //TODO сегодня добить + тесты по ост классам
    }
}