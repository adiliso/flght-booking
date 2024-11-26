package az.edu.turing.domain.dao.impl.postgres;

import az.edu.turing.config.PostgresConfig;
import az.edu.turing.domain.dao.abstracts.FlightDao;
import az.edu.turing.domain.entity.FlightEntity;
import az.edu.turing.model.dto.request.FlightSearchRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static az.edu.turing.model.dto.constants.PostgresQueries.*;

public class FlightDaoPostgres extends FlightDao {

    private final PostgresConfig postgresConfig;

    public FlightDaoPostgres() {
        postgresConfig = new PostgresConfig();

        try (Connection connection = postgresConfig.getConnection();
             PreparedStatement ps = connection.prepareStatement(CREATE_TABLE_FLIGHTS)) {

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<FlightEntity> findAll() {
        String query = "SELECT * FROM flights";
        Set<FlightEntity> flights = new HashSet<>();

        try (Connection connection = postgresConfig.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                flights.add(resultSetToEntity(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }

    private FlightEntity resultSetToEntity(ResultSet rs) throws SQLException {
        return new FlightEntity(
                rs.getLong("id"),
                rs.getTimestamp("departure_datetime").toLocalDateTime(),
                rs.getString("destination_point"),
                rs.getInt("total_seats"),
                rs.getInt("free_seats")
        );
    }

    @Override
    public FlightEntity create(FlightEntity flightEntity) {
        String query = "INSERT INTO flights (departure_datetime, destination_point, total_seats, free_seats)" +
                "values (?, ?, ?, ?) RETURNING *";

        try (Connection connection = postgresConfig.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setTimestamp(1, Timestamp.valueOf(flightEntity.getDepartureDateTime()));
            ps.setString(2, flightEntity.getDestinationPoint());
            ps.setInt(3, flightEntity.getTotalSeats());
            ps.setInt(4, flightEntity.getFreeSeats());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                flightEntity = resultSetToEntity(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flightEntity;
    }

    @Override
    public Optional<FlightEntity> getById(Long id) {
        String query = "SELECT * FROM flights WHERE id = ?";

        try (Connection connection = postgresConfig.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(resultSetToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void saveChanges() {
    }

    @Override
    public Set<FlightEntity> findAllInNext24Hours() {
        String query = "SELECT * FROM flights WHERE departure_datetime BETWEEN NOW() AND NOW() + INTERVAL '1 DAY';";
        Set flights = new HashSet<>();

        try (Connection connection = postgresConfig.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                flights.add(resultSetToEntity(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }

    @Override
    public Set<FlightEntity> search(FlightSearchRequest request) {
        String query = "SELECT * FROM flights WHERE destination_point = ? AND departure_datetime::DATE = ?::DATE AND " +
                "free_seats >= ?;";
        Set<FlightEntity> flights = new HashSet<>();

        try (Connection connection = postgresConfig.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, request.getDestinationPoint());
            ps.setTimestamp(2, Timestamp.valueOf(request.getDate().atStartOfDay()));
            ps.setInt(3, request.getNumberOfPeople());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                flights.add(resultSetToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }
}
