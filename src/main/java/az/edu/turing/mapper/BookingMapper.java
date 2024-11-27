package az.edu.turing.mapper;

import az.edu.turing.domain.entity.BookingEntity;
import az.edu.turing.model.dto.response.BookingResponse;

import java.util.stream.Collectors;

public class BookingMapper {

    public BookingResponse toResponse(BookingEntity entity) {
        return new BookingResponse(
                entity.getFlight().getId(),
                new String[]{entity.getCreatedBy().getName(), entity.getCreatedBy().getLastName()},
                entity.getPassengers()
                        .stream()
                        .map(p -> new String[]{p.getName(), p.getLastName()})
                        .collect(Collectors.toList()),
                entity.getActive()
        );
    }
}
