package az.edu.turing.model.dto.response;

import java.util.List;

public class BookingResponse {

    private long id;
    private long flight_id;
    private String[] createdBy;
    private List<String[]> passengers;
    private String isActive;

    public BookingResponse(long id, long flight_id, String[] createdBy, List<String[]> passengers, String isActive) {
        this.id = id;
        this.flight_id = flight_id;
        this.createdBy = createdBy;
        this.passengers = passengers;
        this.isActive = isActive;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String isActive() {
        return isActive;
    }

    public void setActive(String active) {
        isActive = active;
    }

    @Override
    public String toString() {
        String s = String.format("%-15s%-15s%-30s%-30s%-15s\n",
                id,
                flight_id,
                createdBy[0] + " " + createdBy[1],
                passengers.get(0)[0] + " " + passengers.get(0)[1],
                isActive);
        StringBuilder sb = new StringBuilder(s);
        for (int i = 1; i < passengers.size(); i++) {
            String[] passenger = passengers.get(i);
            sb.append(String.format("%-60s%-30s\n", "", passenger[0] + " " + passenger[1]));
        }
        return sb.toString();
    }
}
