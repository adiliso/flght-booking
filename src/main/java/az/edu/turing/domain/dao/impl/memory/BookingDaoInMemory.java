package az.edu.turing.domain.dao.impl.memory;

import az.edu.turing.domain.dao.abstracts.BookingDao;
import az.edu.turing.domain.entity.BookingEntity;
import az.edu.turing.exception.NotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class BookingDaoInMemory extends BookingDao {

    Map<Long, BookingEntity> BOOKINGS = new HashMap<>();

    private static long idCounter = 1;

    @Override
    public Collection<BookingEntity> findAll() {
        return new ArrayList<>(BOOKINGS.values());
    }

    @Override
    public BookingEntity create(BookingEntity bookingEntity) {
        bookingEntity.setId(idCounter++);
        BOOKINGS.put(bookingEntity.getId(), bookingEntity);
        return bookingEntity;
    }

    @Override
    public Optional<BookingEntity> getById(Long id) {
        return Optional.ofNullable(BOOKINGS.get(id));
    }

    @Override
    public Set<BookingEntity> findAllByPassengerId(long passengerId) {
        return BOOKINGS
                .values()
                .stream()
                .filter(e -> e.getCreatedBy().getId() == passengerId ||
                        e.getPassengers()
                                .stream()
                                .anyMatch(p -> p.getId() == passengerId))
                .collect(Collectors.toSet());
    }

    @Override
    public void saveChanges() {
    }

    @Override
    public boolean cancelBooking(long bookingId) {
        if (existsById(bookingId)) throw new NotFoundException("Booking with id " + bookingId + " not found");

        if (!BOOKINGS.get(bookingId).getActive()) return false;
        BOOKINGS.get(bookingId).setActive(false);
        return true;
    }

    @Override
    public boolean existsById(long id) {
        return BOOKINGS.containsKey(id);
    }
}
