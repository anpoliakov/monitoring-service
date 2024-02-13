package by.anpoliakov.domain.entity;

import java.math.BigInteger;
import java.util.Objects;

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

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeterType meterType = (MeterType) o;
        return Objects.equals(id, meterType.id) && Objects.equals(typeName, meterType.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeName);
    }
}
