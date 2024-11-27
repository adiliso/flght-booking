package az.edu.turing.model.dto.request;

public class FlightCreateRequest {

    private String departureDate;
    private String departureTime;
    private String destinationPoint;
    private int totalSeats;

    public FlightCreateRequest(String departureDate, String departureTime, String destinationPoint, int totalSeats) {
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.destinationPoint = destinationPoint;
        this.totalSeats = totalSeats;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
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
