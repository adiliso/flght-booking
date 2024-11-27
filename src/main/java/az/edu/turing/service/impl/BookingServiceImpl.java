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

    public BookingServiceImpl(BookingDao bookingDao, BookingMapper mapper) {
        this.bookingDao = bookingDao;
        this.mapper = mapper;
    }

    @Override
    public BookingResponse create(BookingCreateRequest request) {
        FlightDao flightDao = new FlightDaoPostgres();
        PassengerDao passengerDao = new PassengerDaoPostgres();

        BookingEntity booking = new BookingEntity(
                flightDao.getById(request.getFlightId()).get(),
                passengerDao.findByNameAndLastName(request.getCreatedBy()[0], request.getCreatedBy()[1]).get(),
                request.getPassengers()
                        .stream()
                        .map(s -> passengerDao.findByNameAndLastName(s[0], s[1]).get())
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
