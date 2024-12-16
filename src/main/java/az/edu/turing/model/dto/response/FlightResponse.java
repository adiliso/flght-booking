package az.edu.turing.model.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public class FlightResponse {

    private long id;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private String destinationPoint;
    private int freeSeats;

    public FlightResponse
            (long id, LocalDate departureDate, LocalTime departureTime, String destinationPoint, int freeSeats) {
        this.id = id;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.destinationPoint = destinationPoint;
        this.freeSeats = freeSeats;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public String getDestinationPoint() {
        return destinationPoint;
    }

    public void setDestinationPoint(String destinationPoint) {
        this.destinationPoint = destinationPoint;
    }

    public int getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    @Override
    public String toString() {
        return "FlightResponse{" +
                "id=" + id +
                ", departureDate='" + departureDate + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", destinationPoint='" + destinationPoint + '\'' +
                ", freeSeats=" + freeSeats +
                '}';
    }
}
