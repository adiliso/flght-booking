package az.edu.turing.model.dto.request;

import java.time.LocalDate;

public class FlightSearchRequest {

    private String destinationPoint;
    private LocalDate date;
    private int numberOfPeople;

    public FlightSearchRequest() {
    }

    public FlightSearchRequest(String destinationPoint, LocalDate date, int numberOfPeople) {
        this.destinationPoint = destinationPoint;
        this.date = date;
        this.numberOfPeople = numberOfPeople;
    }

    public String getDestinationPoint() {
        return destinationPoint;
    }

    public void setDestinationPoint(String destinationPoint) {
        this.destinationPoint = destinationPoint;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }
}
