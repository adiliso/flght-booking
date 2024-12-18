package az.edu.turing.service.impl;

import az.edu.turing.domain.dao.abstracts.FlightDao;
import az.edu.turing.domain.entity.FlightEntity;
import az.edu.turing.exception.NotFoundException;
import az.edu.turing.mapper.FlightMapper;
import az.edu.turing.model.dto.request.FlightCreateRequest;
import az.edu.turing.model.dto.request.FlightSearchRequest;
import az.edu.turing.model.dto.response.FlightResponse;
import az.edu.turing.service.FlightService;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class FlightServiceImpl implements FlightService {

    private final FlightDao flightDao;
    private final FlightMapper mapper;

    public FlightServiceImpl(FlightDao flightDao) {
        this.flightDao = flightDao;
        mapper = new FlightMapper();
    }

    @Override
    public FlightResponse create(FlightCreateRequest request) {
        FlightEntity flight = new FlightEntity(
                request.getDateTime(),
                request.getDestinationPoint(),
                request.getTotalSeats()
        );
        FlightEntity savedFlight = flightDao.create(flight);
        return mapper.toResponse(savedFlight);
    }

    @Override
    public Set<FlightResponse> findAllInNext24Hours() {
        return flightDao.findAllInNext24Hours()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public FlightResponse showInfo(long flightId) {
        Optional<FlightEntity> flight = flightDao.getById(flightId);
        if (flight.isPresent()) {
            return mapper.toResponse(flight.get());
        }
        throw new NotFoundException("Flight with id " + flightId + " not found");
    }

    @Override
    public Set<FlightResponse> search(FlightSearchRequest request) {
        return flightDao.search(request)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toSet());
    }
}
