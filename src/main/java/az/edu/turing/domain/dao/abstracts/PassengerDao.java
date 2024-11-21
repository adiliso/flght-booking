package az.edu.turing.domain.dao.abstracts;

import az.edu.turing.domain.dao.DAO;
import az.edu.turing.domain.entity.PassengerEntity;

import java.util.Optional;

public abstract class PassengerDao implements DAO<PassengerEntity, Long> {

    public abstract Optional<PassengerEntity> findByNameAndLastName(String name, String lastName);
}
