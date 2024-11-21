package az.edu.turing.domain.dao.impl.file;

import az.edu.turing.domain.dao.abstracts.BookingDao;
import az.edu.turing.domain.dao.abstracts.PassengerDao;
import az.edu.turing.domain.entity.BookingEntity;
import az.edu.turing.domain.entity.PassengerEntity;
import az.edu.turing.exception.NotFoundException;
import az.edu.turing.util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookingFileDao extends BookingDao {

    private static final FileUtil<BookingEntity> fileUtil = new FileUtil<>(System.getenv("BOOKINGS_FILE"));
    private static final List<BookingEntity> BOOKINGS = fileUtil.readObject();

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
        fileUtil.writeObject(BOOKINGS);
    }

    @Override
    public List<BookingEntity> findAllByPassengerNameAndSurname(String name, String lastName) throws NotFoundException {
        PassengerDao passengerDao = new PassengerFileDao();
        Optional<PassengerEntity> optionalPassenger = passengerDao.findByNameAndLastName(name, lastName);
        if (!optionalPassenger.isPresent()) {
            throw new NotFoundException("Passenger not found");
        }
        PassengerEntity passenger = optionalPassenger.get();
        return BOOKINGS
                .stream()
                .filter(e -> e.getCreatedBy().equals(passenger) ||
                        e.getPassengers()
                                .stream()
                                .anyMatch(p -> p.equals(passenger)))
                .collect(Collectors.toList());
    }

    private long idGenerator() {
        if (BOOKINGS.isEmpty()) {
            return 1;
        }
        return BOOKINGS.get(BOOKINGS.size() - 1).getId() + 1;
    }
}
