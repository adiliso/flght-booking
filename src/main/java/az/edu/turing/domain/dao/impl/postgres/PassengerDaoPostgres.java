package az.edu.turing.domain.dao.impl.postgres;

import az.edu.turing.config.PostgresConfig;
import az.edu.turing.domain.dao.abstracts.PassengerDao;
import az.edu.turing.domain.entity.PassengerEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static az.edu.turing.model.dto.constants.PostgresQueries.*;

public class PassengerDaoPostgres extends PassengerDao {

    private final PostgresConfig postgresConfig;

    public PassengerDaoPostgres() {
        this.postgresConfig = new PostgresConfig();

        try (Connection connection = postgresConfig.getConnection();
             PreparedStatement cs = connection.prepareStatement(CREATE_TABLE_PASSENGER)) {
            cs.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<PassengerEntity> findAll() {
        String query = "SELECT * FROM passengers";
        Set<PassengerEntity> passengers = new HashSet<>();

        try (Connection connection = postgresConfig.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                passengers.add(resultSetToEntity(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passengers;
    }

    private PassengerEntity resultSetToEntity(ResultSet rs) throws SQLException {
        return new PassengerEntity(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("last_name")
        );
    }

    @Override
    public PassengerEntity create(PassengerEntity passengerEntity) {
        Optional<PassengerEntity> passenger = findByNameAndLastName(
                passengerEntity.getName(), passengerEntity.getLastName());

        if (passenger.isPresent()) {
            return passenger.get();
        }

        String query = "INSERT INTO passengers(name, last_name) VALUES (?, ?) RETURNING *";

        try (Connection connection = postgresConfig.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, passengerEntity.getName());
            ps.setString(2, passengerEntity.getLastName());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                passengerEntity = resultSetToEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passengerEntity;
    }

    @Override
    public Optional<PassengerEntity> getById(Long id) {
        String query = "SELECT * FROM passengers WHERE id = ?";
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
    public Optional<PassengerEntity> findByNameAndLastName(String name, String lastName) {
        String query = "SELECT * FROM passengers WHERE name = ? AND last_name = ?";
        try (Connection connection = postgresConfig.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, name);
            ps.setString(2, lastName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(resultSetToEntity(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
