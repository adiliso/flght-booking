package az.edu.turing.model.dto.response;

import java.util.Arrays;
import java.util.List;

public class BookingResponse {

    private long flight_id;
    private String[] createdBy;
    private List<String[]> passengers;
    private boolean isActive;

    public BookingResponse(long flight_id, String[] createdBy, List<String[]> passengers, boolean isActive) {
        this.flight_id = flight_id;
        this.createdBy = createdBy;
        this.passengers = passengers;
        this.isActive = isActive;
    }

    public long getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(long flight_id) {
        this.flight_id = flight_id;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "BookingResponse{" +
                "flight_id=" + flight_id +
                ", createdBy=" + Arrays.toString(createdBy) +
                ", passengers=" + passengers +
                ", isActive=" + isActive +
                '}';
    }
}
