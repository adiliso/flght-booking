package az.edu.turing;

import az.edu.turing.domain.dao.abstracts.BookingDao;
import az.edu.turing.domain.dao.abstracts.FlightDao;
import az.edu.turing.domain.dao.abstracts.PassengerDao;
import az.edu.turing.domain.dao.impl.postgres.BookingDaoPostgres;
import az.edu.turing.domain.dao.impl.postgres.FlightDaoPostgres;
import az.edu.turing.domain.dao.impl.postgres.PassengerDaoPostgres;
import az.edu.turing.domain.entity.FlightEntity;
import az.edu.turing.domain.entity.PassengerEntity;
import az.edu.turing.model.dto.request.FlightSearchRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {
        PassengerDao passengerDao =
//                new PassengerDaoInMemory();
//                new PassengerFileDao();
                new PassengerDaoPostgres();

        FlightDao flightDao =
//                new FlightDaoInMemory();
//                new FlightFileDao();
                new FlightDaoPostgres();

        BookingDao bookingDao =
//                new BookingDaoInMemory();
//                new BookingFileDao();
                new BookingDaoPostgres();

        FlightEntity flight = new FlightEntity(LocalDateTime.now(), "Baku", 60);
        FlightEntity flight1 = new FlightEntity(LocalDateTime.now().plusHours(2), "Baku", 60);
        FlightEntity flight2 = new FlightEntity(LocalDateTime.now().plusHours(34), "Baku", 60);
        FlightEntity flight3 = new FlightEntity(LocalDateTime.of(LocalDate.of(2024, 11, 26), LocalTime.of(0, 5)), "Baku", 60);
//        flightDao.create(flight3);
        FlightSearchRequest flightSearchRequest = new FlightSearchRequest(
                "Baku",
                LocalDate.now(),
                23
        );
//        System.out.println(flightDao.search(flightSearchRequest));
//        System.out.println(flightDao.findAll());

        FlightEntity flightEntity = flightDao.getById(8L).get();
        PassengerEntity passenger = passengerDao.getById(1L).get();
        PassengerEntity passenger1 = passengerDao.getById(11L).get();
//        System.out.println(passengerDao.findAll());

        List<PassengerEntity> passengers = new ArrayList<>();
        passengers.add(passenger);
        passengers.add(passenger1);

//
//        BookingEntity booking = new BookingEntity(flightEntity, passenger, passengers, true);
//        bookingDao.create(booking);

//        System.out.println(bookingDao.findAll());
        System.out.println(bookingDao.existsById(2));

//        System.out.println(bookingDao.findAll());
//
//        passengerDao.saveChanges();
//        flightDao.saveChanges();
//        bookingDao.saveChanges();

//        System.out.println(passengerDao.findAll());
//        System.out.println(flightDao.findAll());
//        System.out.println(bookingDao.findAll());
    }

}
