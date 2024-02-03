package by.anpoliakov.service.impl;

import by.anpoliakov.domain.entity.MeterType;
import by.anpoliakov.exception.MeterTypeException;
import by.anpoliakov.repository.MeterTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MeterTypeServiceImplTest {

    @InjectMocks
    private MeterTypeServiceImpl meterTypeService;
    @Mock
    private MeterTypeRepository repoMeterType;
    private String testTypeMeter = "test meter type";

    @BeforeEach
    public void prepareRepo(){
        repoMeterType.add(testTypeMeter);
    }

    @Test
    void addMeterType_ifMeterTypeExist_throwMeterTypeException() {
        assertThrows(MeterTypeException.class, () -> meterTypeService.addMeterType(testTypeMeter));
    }

    @Test
    void getMeterType_shouldReturnMeterType() {
        when(repoMeterType.getMeterType(testTypeMeter)).thenReturn(Optional.ofNullable(new MeterType(testTypeMeter)));

        MeterType meterType = meterTypeService.getMeterType(testTypeMeter);
        Assertions.assertEquals(meterType.getTypeName(), testTypeMeter);
    }

    @Test
    void getNamesMetersTypes_shouldReturnListNamesOfMeter() {
        List<String> actualList = Arrays.asList("Test name one", "Test name two");
        when(repoMeterType.getNamesMetersTypes()).thenReturn(Optional.of(actualList));

        List<String> expectedList = meterTypeService.getNamesMetersTypes();
        Assertions.assertEquals(expectedList,actualList);
    }
}