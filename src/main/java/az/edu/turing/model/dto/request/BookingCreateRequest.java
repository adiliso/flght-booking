package az.edu.turing.model.dto.request;

import java.util.List;

public class BookingCreateRequest {

    private long flightId;
    private String[] createdBy;
    private List<String[]> passengers;

    public BookingCreateRequest(long flightId, String[] createdBy, List<String[]> passengers) {
        this.flightId = flightId;
        this.createdBy = createdBy;
        this.passengers = passengers;
    }

    public long getFlightId() {
        return flightId;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }

    public String[] getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String[] createdBy) {
        this.createdBy = createdBy;
    }

    public List<String[]> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<String[]> passengers) {
        this.passengers = passengers;
    }
}
