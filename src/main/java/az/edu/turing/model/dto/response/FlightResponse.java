package az.edu.turing.model.dto.response;

public class FlightResponse {

    private long id;
    private String departureDate;
    private String departureTime;
    private String destinationPoint;
    private int freeSeats;

    public FlightResponse(long id, String departureDate, String departureTime, String destinationPoint, int freeSeats) {
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
