package az.edu.turing.domain.dao.abstracts;

import az.edu.turing.domain.dao.DAO;
import az.edu.turing.domain.entity.PassengerEntity;

public abstract class PassengerDao implements DAO<PassengerEntity, Long> {

    public abstract boolean exists(String name, String lastName);
}
