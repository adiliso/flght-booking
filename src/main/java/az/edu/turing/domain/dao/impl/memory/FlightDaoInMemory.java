package az.edu.turing.domain.dao.impl.memory;

import az.edu.turing.domain.dao.abstracts.FlightDao;
import az.edu.turing.domain.entity.FlightEntity;
import az.edu.turing.model.dto.request.FlightSearchRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FlightDaoInMemory extends FlightDao {

    private static final Map<Long, FlightEntity> FLIGHTS = new HashMap<>();

    private static long idCounter = 1;

    @Override
    public List<FlightEntity> findAll() {
        return new ArrayList<>(FLIGHTS.values());
    }

    @Override
    public FlightEntity create(FlightEntity flightEntity) {
        flightEntity.setId(idCounter++);
        FLIGHTS.put(flightEntity.getId(), flightEntity);
        return flightEntity;
    }

    @Override
    public Optional<FlightEntity> getById(Long id) {
        return Optional.ofNullable(FLIGHTS.get(id));
    }

    @Override
    public List<FlightEntity> findAllInNext24Hours() {
        return FLIGHTS
                .values()
                .stream()
                .filter(e -> e.getDepartureDateTime().isAfter(LocalDateTime.now()) &&
                        e.getDepartureDateTime().isBefore(LocalDateTime.now().plusHours(24))).collect(Collectors.toList());
    }

    @Override
    public List<FlightEntity> search(FlightSearchRequest request) {
        return FLIGHTS
                .values()
                .stream()
                .filter(e -> e.getDestinationPoint().equals(request.getDestinationPoint()) &&
                        e.getDepartureDateTime().toLocalDate().equals(request.getDate()) &&
                        e.getFreeSeats() >= request.getNumberOfPeople())
                .collect(Collectors.toList());
    }
}
