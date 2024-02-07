package by.anpoliakov.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class MeterReading {
    private BigInteger id;
    private BigInteger userId;
    private BigInteger meterTypeId;
    private Integer readings;
    private LocalDateTime date;

    public MeterReading(BigInteger id, BigInteger userId, BigInteger meterTypeId, Integer readings, LocalDateTime date) {
        this.id = id;
        this.userId = userId;
        this.meterTypeId = meterTypeId;
        this.readings = readings;
        this.date = date;
    }

    public MeterReading(BigInteger userId, BigInteger meterTypeId, Integer readings, LocalDateTime date) {
        this.userId = userId;
        this.meterTypeId = meterTypeId;
        this.readings = readings;
        this.date = date;
    }
}
