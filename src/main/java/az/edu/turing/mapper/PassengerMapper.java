package az.edu.turing.mapper;

import az.edu.turing.domain.entity.PassengerEntity;
import az.edu.turing.model.dto.PassengerDto;

public class PassengerMapper {

    public PassengerDto toResponse(PassengerEntity entity) {
        return new PassengerDto(
                entity.getName(),
                entity.getLastName()
        );
    }
}
