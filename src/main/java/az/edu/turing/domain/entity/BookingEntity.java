package az.edu.turing.domain.entity;

import java.io.Serializable;
import java.util.List;

public class BookingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private FlightEntity flight;
    private PassengerEntity createdBy;
    private List<PassengerEntity> passengers;
    private Boolean isActive;

    public BookingEntity(FlightEntity flight, PassengerEntity createdBy, List<PassengerEntity> passengers, Boolean isActive) {
        this.flight = flight;
        this.createdBy = createdBy;
        this.passengers = passengers;
        this.isActive = true;
    }

    public BookingEntity(Long id, FlightEntity flight, PassengerEntity createdBy, List<PassengerEntity> passengers) {
        this.id = id;
        this.flight = flight;
        this.createdBy = createdBy;
        this.passengers = passengers;
        this.isActive = true;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FlightEntity getFlight() {
        return flight;
    }

    public void setFlight(FlightEntity flight) {
        this.flight = flight;
    }

    public PassengerEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(PassengerEntity createdBy) {
        this.createdBy = createdBy;
    }

    public List<PassengerEntity> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerEntity> passengers) {
        this.passengers = passengers;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "BookingEntity{" +
                "id=" + id +
                ", flight=" + flight +
                ", createdBy=" + createdBy +
                ", passengers=" + passengers +
                ", isActive=" + isActive +
                '}';
    }
}
