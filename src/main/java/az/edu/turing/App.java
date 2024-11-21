package az.edu.turing;

import az.edu.turing.domain.dao.abstracts.BookingDao;
import az.edu.turing.domain.dao.abstracts.FlightDao;
import az.edu.turing.domain.dao.abstracts.PassengerDao;
import az.edu.turing.domain.dao.impl.file.BookingFileDao;
import az.edu.turing.domain.dao.impl.file.FlightFileDao;
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
        PassengerDao passengerDao =
//                new PassengerDaoInMemory();
                new PassengerFileDao();

        FlightDao flightDao =
//                new FlightDaoInMemory();
                new FlightFileDao();

        BookingDao bookingDao =
//                new BookingDaoInMemory();
                new BookingFileDao();

        FlightEntity flight = new FlightEntity(LocalDateTime.now(), "Baku", 60);
        flightDao.create(flight);

        PassengerEntity passenger = new PassengerEntity("Adil", "Ismayilov");
        PassengerEntity passenger1 = new PassengerEntity("Parviz", "Muslumov");
        passengerDao.create(passenger);
        passengerDao.create(passenger1);

        List<PassengerEntity> passengers = new ArrayList<>();
        passengers.add(passenger);
        passengers.add(passenger1);

        BookingEntity booking = new BookingEntity(flight, passenger, passengers, true);
        bookingDao.create(booking);

        passengerDao.saveChanges();
        flightDao.saveChanges();
        bookingDao.saveChanges();

//        System.out.println(passengerDao.findAll());
        System.out.println(flightDao.findAll());
//        System.out.println(bookingDao.findAll());
    }

}
