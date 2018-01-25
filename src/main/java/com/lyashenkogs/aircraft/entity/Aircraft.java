package com.lyashenkogs.aircraft.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Aircraft implements Serializable {
    private long id;
    private String name;
    private BigDecimal capacity;
    private BigDecimal carryingCapacity;
    private BigDecimal fuelConsumptionPerHour;
    private BigDecimal flyRange;

    public Aircraft(long id, String name, BigDecimal capacity, BigDecimal carryingCapacity, BigDecimal fuelConsumptionPerHour, BigDecimal flyRange) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.carryingCapacity = carryingCapacity;
        this.fuelConsumptionPerHour = fuelConsumptionPerHour;
        this.flyRange = flyRange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aircraft aircraft = (Aircraft) o;
        return id == aircraft.id &&
                Objects.equals(name, aircraft.name) &&
                Objects.equals(capacity, aircraft.capacity) &&
                Objects.equals(carryingCapacity, aircraft.carryingCapacity) &&
                Objects.equals(fuelConsumptionPerHour, aircraft.fuelConsumptionPerHour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, capacity, carryingCapacity, fuelConsumptionPerHour);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Aircraft{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", carryingCapacity=" + carryingCapacity +
                ", fuelConsumptionPerHour=" + fuelConsumptionPerHour +
                ", flyRange=" + flyRange +
                '}';
    }

    public BigDecimal getCapacity() {
        return capacity;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getCarryingCapacity() {
        return carryingCapacity;
    }

    public void setCarryingCapacity(BigDecimal carryingCapacity) {
        this.carryingCapacity = carryingCapacity;
    }

    public BigDecimal getFuelConsumptionPerHour() {
        return fuelConsumptionPerHour;
    }

    public void setFuelConsumptionPerHour(BigDecimal fuelConsumptionPerHour) {
        this.fuelConsumptionPerHour = fuelConsumptionPerHour;
    }

    public BigDecimal getFlyRange() {
        return flyRange;
    }

    public void setFlyRange(BigDecimal flyRange) {
        this.flyRange = flyRange;
    }
}
