package az.edu.turing.domain.dao.impl.memory;

import az.edu.turing.domain.dao.abstracts.PassengerDao;
import az.edu.turing.domain.entity.PassengerEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PassengerDaoInMemory extends PassengerDao {

    private static Map<Long, PassengerEntity> PASSENGERS;

    private static long idCounter = 1;

    public PassengerDaoInMemory() {
        PASSENGERS = new HashMap<>();
    }

    @Override
    public Collection<PassengerEntity> findAll() {
        return new ArrayList<>(PASSENGERS.values());
    }

    @Override
    public PassengerEntity create(PassengerEntity passengerEntity) {
        Optional<PassengerEntity> passenger =
                findByNameAndLastName(passengerEntity.getName(), passengerEntity.getLastName());
        if (passenger.isPresent()) {
            return passenger.get();
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
    public Optional<PassengerEntity> findByNameAndLastName(String name, String lastName) {
        return PASSENGERS
                .values()
                .stream()
                .filter(e -> e.getName().equals(name) && e.getLastName().equals(lastName))
                .findFirst();
    }

    @Override
    public void saveChanges() {
    }
}
