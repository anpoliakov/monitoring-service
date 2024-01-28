package by.anpoliakov.domain.entities;

import lombok.EqualsAndHashCode;

/** Представляет тип счётчика */
@EqualsAndHashCode
public class MeterType {
    private String typeName;

    public MeterType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
