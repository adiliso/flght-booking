package az.edu.turing;

import az.edu.turing.domain.dao.abstracts.BookingDao;
import az.edu.turing.domain.dao.abstracts.FlightDao;
import az.edu.turing.domain.dao.abstracts.PassengerDao;
import az.edu.turing.domain.dao.impl.file.PassengerFileDao;
import az.edu.turing.domain.dao.impl.memory.BookingDaoInMemory;
import az.edu.turing.domain.dao.impl.memory.FlightDaoInMemory;
import az.edu.turing.domain.dao.impl.memory.PassengerDaoInMemory;
import az.edu.turing.domain.entity.BookingEntity;
import az.edu.turing.domain.entity.FlightEntity;
import az.edu.turing.domain.entity.PassengerEntity;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class App {


    public static void main(String[] args) {
        PassengerFileDao passengerDao = new PassengerFileDao();
//        FlightDao flightDao = new FlightDaoInMemory();
//        BookingDao bookingDao = new BookingDaoInMemory();
//
//        FlightEntity flight = new FlightEntity(LocalDateTime.now(), "Baku", 60);

        PassengerEntity passenger = new PassengerEntity("Adil", "Ismayilov");
        PassengerEntity passenger1 = new PassengerEntity("Parviz", "Muslumov");

//        List<PassengerEntity> passengers = new ArrayList<>();
//        passengers.add(passenger);
//
//        BookingEntity booking = new BookingEntity(flight, passenger, passengers, true);

        passengerDao.create(passenger);
        passengerDao.create(passenger1);
        passengerDao.saveChanges();
        System.out.println(passengerDao.findAll());
    }

}
