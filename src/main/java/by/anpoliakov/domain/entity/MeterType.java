package by.anpoliakov.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigInteger;

@Getter
@EqualsAndHashCode
/** Класс представляет собой тип счётчика */
public class MeterType {
    private BigInteger id;
    private String typeName;

    public MeterType(BigInteger id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    public MeterType(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return typeName;
    }
}
