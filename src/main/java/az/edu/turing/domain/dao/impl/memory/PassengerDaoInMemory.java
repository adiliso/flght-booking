package az.edu.turing.domain.dao.impl.memory;

import az.edu.turing.domain.dao.abstracts.PassengerDao;
import az.edu.turing.domain.entity.PassengerEntity;
import az.edu.turing.exception.AlreadyExistsException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PassengerDaoInMemory extends PassengerDao {

    private static final Map<Long, PassengerEntity> PASSENGERS = new HashMap<>();

    private static long idCounter = 1;

    @Override
    public Collection<PassengerEntity> findAll() {
        return new ArrayList<>(PASSENGERS.values());
    }

    @Override
    public PassengerEntity create(PassengerEntity passengerEntity) {
        if (exists(passengerEntity.getName(), passengerEntity.getLastName())) {
            throw new AlreadyExistsException("Passenger already exists");
        }
        passengerEntity.setId(idCounter++);
        PASSENGERS.put(passengerEntity.getId(), passengerEntity);
        return passengerEntity;
    }

    @Override
    public Optional<PassengerEntity> getById(Long id) {
        return Optional.ofNullable(PASSENGERS.get(id));
    }

    @Override
    public boolean exists(String name, String lastName) {
        return PASSENGERS
                .values()
                .stream()
                .anyMatch(p -> p.getName().equals(name) && p.getLastName().equals(lastName));
    }
}
