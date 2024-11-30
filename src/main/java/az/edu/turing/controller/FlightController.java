package az.edu.turing.controller;

import az.edu.turing.model.dto.request.FlightCreateRequest;
import az.edu.turing.model.dto.request.FlightSearchRequest;
import az.edu.turing.model.dto.response.FlightResponse;
import az.edu.turing.service.FlightService;

import java.util.Set;

public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    public FlightResponse create(FlightCreateRequest request) {
        return flightService.create(request);
    }

    public Set<FlightResponse> findAllInNext24Hours() {
        return (Set<FlightResponse>) flightService.findAllInNext24Hours();
    }

    public FlightResponse showInfo(long flightId) {
        return flightService.showInfo(flightId);
    }

    public Set<FlightResponse> search(FlightSearchRequest request) {
        return (Set<FlightResponse>) flightService.search(request);
    }
}
