package az.edu.turing.controller;

import az.edu.turing.model.dto.PassengerDto;
import az.edu.turing.service.PassengerService;

public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    public PassengerDto create(PassengerDto request) {
        return passengerService.create(request);
    }
}
