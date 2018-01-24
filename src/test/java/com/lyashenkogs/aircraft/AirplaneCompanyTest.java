package com.lyashenkogs.aircraft;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class AirplaneCompanyTest {

    @Test
    public void airplanesWorkflow() {
        //Given an airplane company;
        AirplaneCompany airplaneCompany = new AirplaneCompany(1L, "someAirplaneCompany");
        //then add aircraft's
        airplaneCompany.add(new Aircraft(1L, "someAircraft1", BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ONE));
        airplaneCompany.add(new Aircraft(2L, "someAircraft2", BigDecimal.valueOf(0.05), BigDecimal.ONE, BigDecimal.TEN, BigDecimal.TEN));
        airplaneCompany.add(new Aircraft(3L, "someAircraft3", BigDecimal.valueOf(0.05), BigDecimal.ONE, BigDecimal.TEN, BigDecimal.TEN));
        //so capacities should equal to sum of aircrafts capacities
        assertEquals(airplaneCompany.getTotalAircraftsCapacity(), new BigDecimal("10.10"));
        assertEquals(airplaneCompany.getTotalAircraftsCarryingCapacity(), BigDecimal.valueOf(12L));
        //by range should be returned particular airplanes
        Set<Aircraft> aircraftsByFuelConsumptionRange = airplaneCompany.getAircraftsByFuelConsumption(new BigDecimal("1.0001"), new BigDecimal("10"));
        assertEquals(2, aircraftsByFuelConsumptionRange.size());
    }

    @Test
    public void getByFlyRangeInAscendingOrder() {
        //Given an airplane company;
        AirplaneCompany airplaneCompany = new AirplaneCompany(1L, "someAirplaneCompany");
        //then add aircraft's
        airplaneCompany.add(new Aircraft(1L, "someAircraft1", BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ONE));
        airplaneCompany.add(new Aircraft(2L, "someAircraft2", BigDecimal.valueOf(0.05), BigDecimal.ONE, BigDecimal.TEN, BigDecimal.TEN));
        airplaneCompany.add(new Aircraft(3L, "someAircraft3", BigDecimal.valueOf(0.05), BigDecimal.ONE, BigDecimal.TEN, BigDecimal.TEN));
        //then output aircrafts by fuel consumption in ascending order
        Assert.assertEquals("AirplaneCompany{id=1, name='someAirplaneCompany',\n" +
                " aircrafts:\n" +
                "Aircraft{id=1, name='someAircraft1', capacity=10, carryingCapacity=10, fuelConsumptionPerHour=1, flyRange=1}\n" +
                "Aircraft{id=2, name='someAircraft2', capacity=0.05, carryingCapacity=1, fuelConsumptionPerHour=10, flyRange=10}\n" +
                "Aircraft{id=3, name='someAircraft3', capacity=0.05, carryingCapacity=1, fuelConsumptionPerHour=10, flyRange=10}}", airplaneCompany.toString());
    }
}