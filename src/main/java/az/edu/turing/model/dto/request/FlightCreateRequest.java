package az.edu.turing.model.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class FlightCreateRequest {

    private LocalDateTime dateTime;
    private String destinationPoint;
    private int totalSeats;

    public FlightCreateRequest(LocalDateTime dateTime, String destinationPoint, int totalSeats) {
        this.dateTime = dateTime;
        this.destinationPoint = destinationPoint;
        this.totalSeats = totalSeats;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDestinationPoint() {
        return destinationPoint;
    }

    public void setDestinationPoint(String destinationPoint) {
        this.destinationPoint = destinationPoint;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }
}
