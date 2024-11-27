package az.edu.turing.domain.dao.impl.file;

import az.edu.turing.domain.dao.abstracts.BookingDao;
import az.edu.turing.domain.entity.BookingEntity;
import az.edu.turing.exception.NotFoundException;
import az.edu.turing.util.FileDaoUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class BookingFileDao extends BookingDao {

    private static final FileDaoUtil<BookingEntity> FILE_DAO_UTIL = new FileDaoUtil<>(System.getenv("BOOKINGS_FILE"));
    private static final List<BookingEntity> BOOKINGS = FILE_DAO_UTIL.readObject();

    @Override
    public List<BookingEntity> findAll() {
        return new ArrayList<>(BOOKINGS);
    }

    @Override
    public BookingEntity create(BookingEntity bookingEntity) {
        bookingEntity.setId(idGenerator());
        BOOKINGS.add(bookingEntity);
        return bookingEntity;
    }

    @Override
    public Optional<BookingEntity> getById(Long id) {
        return BOOKINGS
                .stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    @Override
    public void saveChanges() {
        FILE_DAO_UTIL.writeObject(BOOKINGS);
    }

    @Override
    public Set<BookingEntity> findAllByPassengerId(long passengerId) {
        return BOOKINGS
                .stream()
                .filter(e -> e.getCreatedBy().getId() == passengerId ||
                        e.getPassengers()
                                .stream()
                                .anyMatch(p -> p.getId() == passengerId))
                .collect(Collectors.toSet());
    }

    private long idGenerator() {
        if (BOOKINGS.isEmpty()) {
            return 1;
        }
        return BOOKINGS.get(BOOKINGS.size() - 1).getId() + 1;
    }

    @Override
    public boolean cancelBooking(long bookingId) {
        if (existsById(bookingId)) throw new NotFoundException("Booking with id " + bookingId + " not found");

        for (BookingEntity booking : BOOKINGS) {
            if (!booking.getActive()) return false;
            booking.setActive(false);
        }
        return true;
    }

    @Override
    public boolean existsById(long id) {
        return BOOKINGS.
                stream().
                anyMatch(p -> p.getId() == id);
    }
}
