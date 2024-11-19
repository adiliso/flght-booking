package az.edu.turing.domain.dao.impl.memory;

import az.edu.turing.domain.dao.abstracts.BookingDao;
import az.edu.turing.domain.entity.BookingEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    public List<BookingEntity> findAllByPassengerNameAndSurname(String name, String lastName) {
        return BOOKINGS
                .values()
                .stream()
                .filter(e -> (e.getCreatedBy().getName().equals(name) && e.getCreatedBy().getLastName().equals(lastName))
                || e.getPassengers().stream().anyMatch(p -> p.getName().equals(name) && p.getLastName().equals(lastName)))
                .collect(Collectors.toList());
    }
}
