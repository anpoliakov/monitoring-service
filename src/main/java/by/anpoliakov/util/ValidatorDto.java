package by.anpoliakov.util;

import by.anpoliakov.domain.dto.ValidatableDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;

import java.util.Set;

public class ValidatorDto {
    private static final Validator VALIDATOR;

    static {
        VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public static void validateDto(ValidatableDTO dto){
        Set<ConstraintViolation<ValidatableDTO>> validate = VALIDATOR.validate(dto);
        if(!validate.isEmpty()){
            throw new ValidationException();
        }
    }
}
