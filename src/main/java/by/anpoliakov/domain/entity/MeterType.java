package by.anpoliakov.domain.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
/** Класс представляет собой тип счётчика */
public class MeterType {
    private String typeName;

    @Override
    public String toString() {
        return typeName;
    }
}
