package az.edu.turing.service.impl;

import az.edu.turing.domain.dao.abstracts.PassengerDao;
import az.edu.turing.domain.entity.PassengerEntity;
import az.edu.turing.mapper.PassengerMapper;
import az.edu.turing.model.dto.PassengerDto;
import az.edu.turing.service.PassengerService;

public class PassengerServiceImpl implements PassengerService {

    private final PassengerDao passengerDao;
    private final PassengerMapper mapper;

    public PassengerServiceImpl(PassengerDao passengerDao) {
        this.passengerDao = passengerDao;
        this.mapper = new PassengerMapper();
    }

    @Override
    public PassengerDto create(PassengerDto request) {
        PassengerEntity passenger = new PassengerEntity(request.getFirstName(), request.getLastName());
        PassengerEntity savedPassenger = passengerDao.create(passenger);
        return mapper.toResponse(savedPassenger);
    }
}
