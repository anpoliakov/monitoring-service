package by.anpoliakov.domain.entity;

import java.math.BigInteger;
import java.time.LocalDateTime;

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

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getMeterTypeId() {
        return meterTypeId;
    }

    public void setMeterTypeId(BigInteger meterTypeId) {
        this.meterTypeId = meterTypeId;
    }

    public Integer getReadings() {
        return readings;
    }

    public void setReadings(Integer readings) {
        this.readings = readings;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "MeterReading{" +
                "id=" + id +
                ", userId=" + userId +
                ", meterTypeId=" + meterTypeId +
                ", readings=" + readings +
                ", date=" + date +
                '}';
    }
}
