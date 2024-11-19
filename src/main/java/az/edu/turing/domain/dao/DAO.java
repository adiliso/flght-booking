package az.edu.turing.domain.dao;

import java.util.Collection;
import java.util.Optional;

public interface DAO<E, I> {

    Collection<E> findAll();

    E create(E e);

    Optional<E> getById(I id);
}
