package az.edu.turing.mapper;

import az.edu.turing.domain.entity.FlightEntity;
import az.edu.turing.model.dto.response.FlightResponse;

public class FlightMapper {

    public FlightResponse toResponse(FlightEntity entity) {
        return new FlightResponse(
                entity.getId(),
                entity.getDepartureDateTime().toLocalDate(),
                entity.getDepartureDateTime().toLocalTime(),
                entity.getDestinationPoint(),
                entity.getFreeSeats()
        );
    }
}
