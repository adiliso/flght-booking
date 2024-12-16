package az.edu.turing.service.impl;

import az.edu.turing.domain.dao.abstracts.BookingDao;
import az.edu.turing.domain.dao.abstracts.FlightDao;
import az.edu.turing.domain.dao.abstracts.PassengerDao;
import az.edu.turing.domain.dao.impl.postgres.FlightDaoPostgres;
import az.edu.turing.domain.dao.impl.postgres.PassengerDaoPostgres;
import az.edu.turing.domain.entity.BookingEntity;
import az.edu.turing.domain.entity.PassengerEntity;
import az.edu.turing.exception.NotFoundException;
import az.edu.turing.mapper.BookingMapper;
import az.edu.turing.model.dto.request.BookingCreateRequest;
import az.edu.turing.model.dto.response.BookingResponse;
import az.edu.turing.service.BookingService;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class BookingServiceImpl implements BookingService {

    private final BookingDao bookingDao;
    private final BookingMapper mapper;

    public BookingServiceImpl(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
        this.mapper = new BookingMapper();
    }

    @Override
    public BookingResponse create(BookingCreateRequest request) {
        if (request.getPassengers().isEmpty()) throw new NotFoundException("Passengers cannot be empty");
        FlightDao flightDao = new FlightDaoPostgres();
        PassengerDao passengerDao = new PassengerDaoPostgres();

        BookingEntity booking = new BookingEntity(
                flightDao.getById(request.getFlightId()).orElseThrow(NotFoundException::new),
                passengerDao.findByNameAndLastName(request.getCreatedBy()[0], request.getCreatedBy()[1])
                        .orElse(passengerDao.create(new PassengerEntity(
                                request.getCreatedBy()[0],
                                request.getCreatedBy()[1]))),
                request.getPassengers()
                        .stream()
                        .map(s -> passengerDao.findByNameAndLastName(s[0], s[1])
                                .orElse(passengerDao.create(new PassengerEntity(
                                        s[0],
                                        s[1]))))
                        .collect(Collectors.toList())
        );

        BookingEntity savedBooking = bookingDao.create(booking);
        return mapper.toResponse(savedBooking);
    }

    @Override
    public boolean cancel(long id) {
        return bookingDao.cancelBooking(id);
    }

    @Override
    public Set<BookingResponse> findAllByPassengerNameAndLastName(String name, String lastName) {
        PassengerDao passengerDao = new PassengerDaoPostgres();
        Optional<PassengerEntity> passenger = passengerDao.findByNameAndLastName(name, lastName);
        if (passenger.isPresent()) {
            return bookingDao.findAllByPassengerId(passenger.get().getId())
                    .stream()
                    .map(mapper::toResponse)
                    .collect(Collectors.toSet());
        }
        throw new NotFoundException("The passenger " + name + " does not exist");
    }
}
