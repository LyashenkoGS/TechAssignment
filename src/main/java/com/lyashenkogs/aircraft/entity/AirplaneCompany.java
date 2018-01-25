package com.lyashenkogs.aircraft.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class AirplaneCompany implements Serializable {
    private long id;
    private String name;
    private Set<Aircraft> aircrafts = new TreeSet<>((Comparator<? super Aircraft> & Serializable) (o1, o2) -> {
        if (o1.getFlyRange().compareTo(o2.getFlyRange()) == 0) {
            return Long.compare(o1.getId(), o2.getId());
        } else {
            return (o1.getFlyRange().compareTo(o2.getFlyRange()));
        }
    });

    public AirplaneCompany(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "AirplaneCompany{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ",\n aircrafts:\n" + aircrafts.stream()
                .map(Aircraft::toString)
                .collect(Collectors.joining("\n")) +
                '}';
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

    public Set<Aircraft> getAircrafts() {
        return aircrafts;
    }

    public void setAircrafts(Set<Aircraft> aircrafts) {
        this.aircrafts = aircrafts;
    }

    public BigDecimal getTotalAircraftsCapacity() {
        return aircrafts.stream()
                .map(Aircraft::getCapacity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalAircraftsCarryingCapacity() {
        return aircrafts.stream()
                .map(Aircraft::getCarryingCapacity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Set<Aircraft> getAircraftsByFuelConsumption(BigDecimal min, BigDecimal max) {
        return aircrafts.stream()
                .filter(aircraft -> {
                    int compareToMin = aircraft.getFuelConsumptionPerHour().compareTo(min);
                    int compareToMax = aircraft.getFuelConsumptionPerHour().compareTo(max);
                    boolean lowerBound = compareToMin == 0 || compareToMin == 1;
                    boolean upperBound = compareToMax == 0 || compareToMax == -1;
                    return lowerBound && upperBound;
                })
                .collect(Collectors.toSet());
    }

    public void add(Aircraft aircraft) {
        if (this.getAircrafts().contains(aircraft)) {
            System.out.println("already contain an aircraft!");
        } else {
            this.aircrafts.add(aircraft);
        }
    }

}
