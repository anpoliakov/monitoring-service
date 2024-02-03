package by.anpoliakov.service.impl;

import by.anpoliakov.domain.entity.MeterType;
import by.anpoliakov.exception.MeterTypeException;
import by.anpoliakov.repository.MeterTypeRepository;
import by.anpoliakov.service.MeterTypeService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * Сервис отвечающий за регистрацию и выдачу типов счётчиков - MeterType
 * */
@AllArgsConstructor
public class MeterTypeServiceImpl implements MeterTypeService {
    private MeterTypeRepository repoMeterType;

    @Override
    public void addMeterType(String typeName) throws MeterTypeException {

        if(typeName.trim().isEmpty() || typeName == null){
            throw new MeterTypeException("Incorrect name of type meter!");
        }

        if(getMeterType(typeName) != null){
            throw new MeterTypeException("This meter already exists in the database!");
        }

        repoMeterType.add(typeName);
    }

    @Override
    public MeterType getMeterType(String typeName) throws MeterTypeException {
        if(typeName.trim().isEmpty() || typeName == null){
            throw new MeterTypeException("Incorrect name of new type meter!");
        }

        Optional<MeterType> meterType = repoMeterType.getMeterType(typeName);
        if(meterType.isEmpty()){
            throw new MeterTypeException("Meter type " + typeName + " is not in the database!");
        }

        return meterType.get();
    }

    @Override
    public List<String> getNamesMetersTypes() throws MeterTypeException {
        Optional<List<String>> namesMetersTypes = repoMeterType.getNamesMetersTypes();
        if(namesMetersTypes.isEmpty()){
            throw new MeterTypeException("There are no meters types in the database!");
        }

        return namesMetersTypes.get();
    }


}
