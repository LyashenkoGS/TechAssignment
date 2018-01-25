package com.lyashenkogs.aircraft;

import com.lyashenkogs.aircraft.entity.Aircraft;
import com.lyashenkogs.aircraft.entity.AirplaneCompany;

import java.io.*;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import static java.lang.System.in;
import static java.lang.System.out;

public class App {
    public static String DEFAULT_STORAGE = "default.txt";
    private static Scanner scanner = new Scanner(in);

    public static void main(String[] args) throws Exception {
        AirplaneCompany airplaneCompany = readStorage();
        out.println("###Airlane application####\n");
        while (true) {
            out.println("Select one of the available actions (1-4): ");
            out.println("1. add aircraft");
            out.println("2. delete aircraft");
            out.println("3. find aircraft by fuel consumption range");
            out.println("4. list aircraft's by flight range in ascending order");
            processInput(airplaneCompany);
        }
    }

    public static void processInput(AirplaneCompany airplaneCompany) throws IOException {
        if (scanner.hasNext()) {
            int action = scanner.nextInt();
            if (action == 1) {
                out.println("specify comma separated aircraft parameters. \"" +
                        "id,name,capacity,carryingCapacity,fuelConsumptionPerHour,flyRange\n" +
                        "Example: 11, someName, 12.0, 12.0, 1, 12.12\n");
                if (scanner.hasNext()) {
                    scanner.nextLine();//technical depth to workaround whitespaces
                    String input = scanner.nextLine();
                    String[] tokens = input.replaceAll("\\s", "").split(",");
                    Aircraft newAircraft = new Aircraft(Long.valueOf(tokens[0]), tokens[1], new BigDecimal(tokens[2]),
                            new BigDecimal(tokens[3]), new BigDecimal(tokens[4]), new BigDecimal(tokens[5]));
                    addAircraft(airplaneCompany, newAircraft);
                }
            } else if (action == 2) {
                out.println("specify aircraft id");
                if (scanner.hasNext()) {
                    scanner.nextLine();//technical depth to workaround whitespaces
                    Long id = Long.valueOf(scanner.nextLine());
                    deleteAircraft(airplaneCompany, id);
                }
            } else if (action == 3) {
                out.println("specify comma separated fuel consumption range inclusively\n" +
                        "Example: 0.04, 10\n");
                if (scanner.hasNext()) {
                    scanner.nextLine();//technical depth to workaround whitespaces
                    String input = scanner.nextLine();
                    String[] tokens = input.replaceAll("\\s", "").split(",");
                    Set<Aircraft> aircraftsByFuelConsumption = airplaneCompany.getAircraftsByFuelConsumption(new BigDecimal(tokens[0]), new BigDecimal(tokens[1]));
                    System.out.println(aircraftsByFuelConsumption + "\n\n");
                }
            } else if (action == 4) {
                System.out.println(airplaneCompany + "\n\n");
            }
        }
    }

    public static void deleteAircraft(AirplaneCompany airplaneCompany, Long id) {
        Optional<Aircraft> first = airplaneCompany.getAircrafts()
                .stream()
                .filter(aircraft -> aircraft.getId() == id)
                .findFirst();
        first.ifPresent(aircraft -> {
            airplaneCompany.getAircrafts().remove(aircraft);
            ObjectOutputStream objectOutputStream;
            try {
                objectOutputStream = new ObjectOutputStream(new FileOutputStream(DEFAULT_STORAGE));
                objectOutputStream.writeObject(airplaneCompany);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("aircraft: " + aircraft + "\n has been removed");
        });
        if (!first.isPresent()) {
            System.out.println("No aircraft with id: " + id);
        }
    }

    public static void addAircraft(AirplaneCompany airplaneCompany, Aircraft newAircraft) throws IOException {
        airplaneCompany.add(newAircraft);
        System.out.println("New aircraft has been created :\n" + newAircraft + "\n\n");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(DEFAULT_STORAGE));
        objectOutputStream.writeObject(airplaneCompany);
    }

    public static AirplaneCompany readStorage() throws IOException, ClassNotFoundException {
        System.out.println("database has not been selected, using the default one");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DEFAULT_STORAGE));
        Object o = ois.readObject();
        return (AirplaneCompany) o;
    }

}
