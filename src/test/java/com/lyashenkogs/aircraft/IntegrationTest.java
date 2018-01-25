package com.lyashenkogs.aircraft;

import com.lyashenkogs.aircraft.entity.Aircraft;
import com.lyashenkogs.aircraft.entity.AirplaneCompany;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

//test gets blocked if run all by maven
@Ignore
public class IntegrationTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private AirplaneCompany defaultAirplaneCompany;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        defaultAirplaneCompany = new AirplaneCompany(1L, "someAirplaneCompany");
        //then add aircraft's
        defaultAirplaneCompany.add(new Aircraft(1L, "someAircraft1", BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ONE));
        defaultAirplaneCompany.add(new Aircraft(2L, "someAircraft2", BigDecimal.valueOf(0.05), BigDecimal.ONE, BigDecimal.TEN, BigDecimal.TEN));
        defaultAirplaneCompany.add(new Aircraft(3L, "someAircraft3", BigDecimal.valueOf(0.05), BigDecimal.ONE, BigDecimal.TEN, BigDecimal.TEN));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void findByFuelConsumption() throws IOException, InterruptedException {
        String userInput = "3\n" +
                "0.04, 10\n";
        InputStream targetStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(targetStream);
        App.processInput(defaultAirplaneCompany);
        assertEquals("specify comma separated fuel consumption range inclusively\n" +
                "Example: 0.04, 10\n" +
                "\n" +
                "[Aircraft{id=3, name='someAircraft3', capacity=0.05, carryingCapacity=1, fuelConsumptionPerHour=10, flyRange=10}, Aircraft{id=2, name='someAircraft2', capacity=0.05, carryingCapacity=1, fuelConsumptionPerHour=10, flyRange=10}, Aircraft{id=1, name='someAircraft1', capacity=10, carryingCapacity=10, fuelConsumptionPerHour=1, flyRange=1}]\n" +
                "\n" +
                "\n", outContent.toString());
    }

    @Test
    public void addAircraft() throws IOException {
        String userInput = "1\n" +
                "11, someName, 12.0, 12.0, 1, 12.12\n";
        InputStream targetStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(targetStream);
        App.processInput(defaultAirplaneCompany);
        assertEquals("specify comma separated aircraft parameters. \"id,name,capacity,carryingCapacity,fuelConsumptionPerHour,flyRange\n" +
                "Example: 11, someName, 12.0, 12.0, 1, 12.12\n" +
                "\n" +
                "New aircraft has been created :\n" +
                "Aircraft{id=11, name='someName', capacity=12.0, carryingCapacity=12.0, fuelConsumptionPerHour=1, flyRange=12.12}\n" +
                "\n" +
                "\n", outContent.toString());
        assertEquals(4, defaultAirplaneCompany.getAircrafts().size());
        //clean up database
        App.deleteAircraft(defaultAirplaneCompany, 11L);
        assertEquals(3, defaultAirplaneCompany.getAircrafts().size());
    }

    @Test
    public void deleteAircraft() throws IOException {
        String userInput = "2\n" +
                "2\n";
        InputStream targetStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(targetStream);
        App.processInput(defaultAirplaneCompany);
        assertEquals("specify aircraft id\n" +
                "aircraft: Aircraft{id=2, name='someAircraft2', capacity=0.05, carryingCapacity=1, fuelConsumptionPerHour=10, flyRange=10}\n" +
                " has been removed\n", outContent.toString());
        //clean up database
        App.addAircraft(defaultAirplaneCompany, new Aircraft(2L, "someAircraft2", BigDecimal.valueOf(0.05), BigDecimal.ONE, BigDecimal.TEN, BigDecimal.TEN));
        assertEquals(3, defaultAirplaneCompany.getAircrafts().size());
    }

    @Test
    public void listAircrafts() throws IOException {
        String userInput = "4\n";
        InputStream targetStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(targetStream);
        App.processInput(defaultAirplaneCompany);
        assertEquals("AirplaneCompany{id=1, name='someAirplaneCompany',\n" +
                " aircrafts:\n" +
                "Aircraft{id=1, name='someAircraft1', capacity=10, carryingCapacity=10, fuelConsumptionPerHour=1, flyRange=1}\n" +
                "Aircraft{id=2, name='someAircraft2', capacity=0.05, carryingCapacity=1, fuelConsumptionPerHour=10, flyRange=10}\n" +
                "Aircraft{id=3, name='someAircraft3', capacity=0.05, carryingCapacity=1, fuelConsumptionPerHour=10, flyRange=10}}\n" +
                "\n" +
                "\n", outContent.toString());
    }

    @Test
    public void initializeStorage() throws IOException, ClassNotFoundException {
        //default storage should have default company with default aircrafts
        assertEquals(defaultAirplaneCompany.toString(), App.readStorage().toString());
    }
}
