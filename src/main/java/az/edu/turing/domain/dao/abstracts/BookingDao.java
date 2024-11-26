package az.edu.turing.domain.dao.abstracts;

import az.edu.turing.domain.dao.DAO;
import az.edu.turing.domain.entity.BookingEntity;

import java.util.Collection;

public abstract class BookingDao implements DAO<BookingEntity, Long> {

    public abstract Collection<BookingEntity> findAllByPassengerId(long passengerId);

    public abstract boolean cancelBooking(long bookingId);

    public abstract boolean existsById(long id);
}
