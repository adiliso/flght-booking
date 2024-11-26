package az.edu.turing.domain.dao.impl.file;

import az.edu.turing.domain.dao.abstracts.FlightDao;
import az.edu.turing.domain.entity.FlightEntity;
import az.edu.turing.model.dto.request.FlightSearchRequest;
import az.edu.turing.util.FileDaoUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FlightFileDao extends FlightDao {

    private static final FileDaoUtil<FlightEntity> FILE_DAO_UTIL = new FileDaoUtil<>(System.getenv("FLIGHTS_FILE"));
    private static final List<FlightEntity> FLIGHTS = FILE_DAO_UTIL.readObject();

    @Override
    public List<FlightEntity> findAll() {
        return new ArrayList<>(FLIGHTS);
    }

    @Override
    public FlightEntity create(FlightEntity flightEntity) {
        flightEntity.setId(idGenerator());
        FLIGHTS.add(flightEntity);
        return flightEntity;
    }

    @Override
    public Optional<FlightEntity> getById(Long id) {
        return FLIGHTS
                .stream()
                .filter(flightEntity -> flightEntity.getId().equals(id))
                .findFirst();
    }

    @Override
    public void saveChanges() {
        FILE_DAO_UTIL.writeObject(FLIGHTS);
    }

    @Override
    public List<FlightEntity> findAllInNext24Hours() {
        LocalDateTime now = LocalDateTime.now();
        return FLIGHTS
                .stream()
                .filter(f -> f.getDepartureDateTime().isAfter(now) &&
                        f.getDepartureDateTime().isBefore(now.plusHours(24)))
                .collect(Collectors.toList());
    }

    @Override
    public List<FlightEntity> search(FlightSearchRequest request) {
        return FLIGHTS
                .stream()
                .filter(e -> e.getDestinationPoint().equals(request.getDestinationPoint()) &&
                        e.getDepartureDateTime().toLocalDate().equals(request.getDate()) &&
                        e.getFreeSeats() >= request.getNumberOfPeople())
                .collect(Collectors.toList());
    }

    private long idGenerator() {
        if (FLIGHTS.isEmpty()) {
            return 1;
        }
        return FLIGHTS.get(FLIGHTS.size() - 1).getId() + 1;
    }
}
