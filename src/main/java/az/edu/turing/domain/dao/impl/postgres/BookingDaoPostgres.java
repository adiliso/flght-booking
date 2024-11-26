package az.edu.turing.domain.dao.impl.postgres;

import az.edu.turing.config.PostgresConfig;
import az.edu.turing.domain.dao.abstracts.BookingDao;
import az.edu.turing.domain.dao.abstracts.FlightDao;
import az.edu.turing.domain.dao.abstracts.PassengerDao;
import az.edu.turing.domain.entity.BookingEntity;
import az.edu.turing.domain.entity.PassengerEntity;
import az.edu.turing.exception.NotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static az.edu.turing.model.dto.constants.PostgresQueries.*;

public class BookingDaoPostgres extends BookingDao {

    private final PostgresConfig postgresConfig;
    private final FlightDao flightDao = new FlightDaoPostgres();
    private final PassengerDao passengerDao = new PassengerDaoPostgres();

    public BookingDaoPostgres() {
        postgresConfig = new PostgresConfig();

        try (Connection connection = postgresConfig.getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(CREATE_TABLE_BOOKINGS);
            statement.executeUpdate(CREATE_TABLE_BOOKINGS_PASSENGERS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<BookingEntity> findAll() {
        Set<BookingEntity> bookings = new HashSet<>();
        String query = "SELECT * FROM bookings;";
        try (Connection connection = postgresConfig.getConnection();
             PreparedStatement ps1 = connection.prepareStatement(query)) {

            ResultSet rs = ps1.executeQuery();
            while (rs.next()) {
                bookings.add(resultSetToEntity(rs));
            }

            return resultSetToEntity(bookings, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    private Set<BookingEntity> resultSetToEntity(Set<BookingEntity> bookings, Connection conn) throws SQLException {
        String query = "SELECT passenger_id FROM bookings_passengers where booking_id=?;";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            for (BookingEntity booking : bookings) {
                List<PassengerEntity> passengers = new ArrayList<>();
                ps.setLong(1, booking.getId());
                ResultSet rs2 = ps.executeQuery();
                while (rs2.next()) {
                    passengers.add(passengerDao.getById(rs2.getLong(1)).get());
                }
                booking.setPassengers(passengers);
            }
        }
        return bookings;
    }

    private BookingEntity resultSetToEntity(ResultSet rs) throws SQLException {
        return new BookingEntity(
                rs.getLong("id"),
                flightDao.getById(rs.getLong("flight_id")).get(),
                passengerDao.getById(rs.getLong("created_by")).get(),
                rs.getBoolean("is_active")
        );
    }

    @Override
    public BookingEntity create(BookingEntity bookingEntity) {
        String query1 = "INSERT INTO bookings (flight_id, created_by, is_active) VALUES (?, ?, ?) RETURNING id";
        String query2 = "INSERT INTO bookings_passengers VALUES (?, ?)";

        try (Connection connection = postgresConfig.getConnection();
             PreparedStatement pst1 = connection.prepareStatement(query1);
             PreparedStatement pst2 = connection.prepareStatement(query2)) {

            pst1.setLong(1, bookingEntity.getFlight().getId());
            pst1.setLong(2, bookingEntity.getCreatedBy().getId());
            pst1.setBoolean(3, bookingEntity.getActive());
            ResultSet rs = pst1.executeQuery();
            if (rs.next()) {
                bookingEntity.setId(rs.getLong(1));
            }

            pst2.setLong(1, bookingEntity.getId());
            for (PassengerEntity passenger : bookingEntity.getPassengers()) {
                pst2.setLong(2, passenger.getId());
                pst2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookingEntity;
    }

    @Override
    public Optional<BookingEntity> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public void saveChanges() {
    }

    @Override
    public Set<BookingEntity> findAllByPassengerId(long passengerId) {
        String query = "SELECT b.id, b.flight_id, b.created_by, b.is_active" +
                "FROM bookings b" +
                "         LEFT JOIN bookings_passengers bp on b.id = bp.booking_id" +
                "         LEFT JOIN passengers p on bp.passenger_id = p.id" +
                "where created_by = ? OR p.id = ?;";
        Set<BookingEntity> bookings = new HashSet<>();
        try (Connection connection = postgresConfig.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, passengerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bookings.add(resultSetToEntity(rs));
            }

            return resultSetToEntity(bookings, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new HashSet<>();
    }

    @Override
    public boolean cancelBooking(long bookingId) {
        if (existsById(bookingId)) throw new NotFoundException("Booking with id " + bookingId + " does not exist");

        String query = "UPDATE bookings SET is_active = true WHERE id = ?;";
        try (Connection connection = postgresConfig.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, bookingId);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean existsById(long id) {
        String query = "SELECT EXISTS(SELECT 1 FROM bookings WHERE id = ?);";

        try (Connection connection = postgresConfig.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
