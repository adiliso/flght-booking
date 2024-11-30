package az.edu.turing.controller;

import az.edu.turing.model.dto.request.BookingCreateRequest;
import az.edu.turing.model.dto.response.BookingResponse;
import az.edu.turing.service.BookingService;

import java.util.Set;

public class BookingController {

    public final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public BookingResponse create(BookingCreateRequest request) {
        return bookingService.create(request);
    }

    public boolean cancel(long id) {
        return bookingService.cancel(id);
    }

    public Set<BookingResponse> findAllByPassengerNameAndLastName(String name, String lastName) {
        return (Set<BookingResponse>) bookingService.findAllByPassengerNameAndLastName(name, lastName);
    }
}
