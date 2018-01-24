package com.lyashenkogs.aircraft;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;

public class App {

    public static void main(String[] args) throws IOException {
        System.out.println("hello");
        FileOutputStream fout = new FileOutputStream("./database.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        AirplaneCompany airplaneCompany = new AirplaneCompany(1L, "someAirplaneCompany");
        //then add aircraft's
        airplaneCompany.add(new Aircraft(1L, "someAircraft1", BigDecimal.TEN, BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ONE));
        airplaneCompany.add(new Aircraft(2L, "someAircraft2", BigDecimal.valueOf(0.05), BigDecimal.ONE, BigDecimal.TEN, BigDecimal.TEN));
        airplaneCompany.add(new Aircraft(3L, "someAircraft3", BigDecimal.valueOf(0.05), BigDecimal.ONE, BigDecimal.TEN, BigDecimal.TEN));
        oos.writeObject(airplaneCompany);
        //TODO save database on ctrl +s
        //TODO add some hierarchi
        //TODO move classes to packages
        //TODO implement IO interaction add/delete company and aircrafts
    }

}
