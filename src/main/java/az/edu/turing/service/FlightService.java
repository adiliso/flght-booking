package az.edu.turing.service;

import az.edu.turing.model.dto.request.FlightCreateRequest;
import az.edu.turing.model.dto.request.FlightSearchRequest;
import az.edu.turing.model.dto.response.FlightResponse;

import java.util.Collection;

public interface FlightService {

    FlightResponse create(FlightCreateRequest request);

    Collection<FlightResponse> findAllInNext24Hours();

    FlightResponse showInfo(long flightId);

    Collection<FlightResponse> search(FlightSearchRequest request);
}
