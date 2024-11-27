package az.edu.turing.service;

import az.edu.turing.model.dto.request.BookingCreateRequest;
import az.edu.turing.model.dto.response.BookingResponse;

import java.util.Collection;

public interface BookingService {

    BookingResponse create(BookingCreateRequest request);

    boolean cancel(long id);

    Collection<BookingResponse> findAllByPassengerNameAndLastName(String name, String lastName);
}
