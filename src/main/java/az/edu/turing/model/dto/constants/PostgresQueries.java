package az.edu.turing.model.dto.constants;

public class PostgresQueries {

    public static String CREATE_TABLE_PASSENGERS =
            "CREATE TABLE IF NOT EXISTS passengers" +
                    "(" +
                    "    id        BIGSERIAL PRIMARY KEY," +
                    "    name      VARCHAR(50) NOT NULL," +
                    "    last_name VARCHAR(50) NOT NULL, " +
                    "    UNIQUE(name, last_name) " +
                    ");";

    public static String CREATE_TABLE_FLIGHTS =
            "CREATE TABLE IF NOT EXISTS flights" +
                    "(" +
                    "    id                 BIGSERIAL PRIMARY KEY," +
                    "    departure_datetime TIMESTAMPTZ   NOT NULL," +
                    "    destination_point  VARCHAR(50) NOT NULL," +
                    "    total_seats        INT         NOT NULL," +
                    "    free_seats         INT         NOT NULL" +
                    ");";

    public static String CREATE_TABLE_BOOKINGS =
            "CREATE TABLE IF NOT EXISTS bookings" +
                    "(" +
                    "    id         BIGSERIAL PRIMARY KEY," +
                    "    flight_id  BIGINT  NOT NULL," +
                    "    created_by BIGINT  NOT NULL," +
                    "    is_active   BOOLEAN NOT NULL," +
                    "    FOREIGN KEY (flight_id) REFERENCES flights (id)," +
                    "    FOREIGN KEY (created_by) REFERENCES passengers (id)" +
                    ");";

    public static String CREATE_TABLE_BOOKINGS_PASSENGERS =
            "CREATE TABLE IF NOT EXISTS bookings_passengers" +
                    "(" +
                    "    booking_id   BIGINT NOT NULL," +
                    "    passenger_id BIGINT NOT NULL," +
                    "    PRIMARY KEY (booking_id, passenger_id)," +
                    "    FOREIGN KEY (booking_id) REFERENCES bookings (id)," +
                    "    FOREIGN KEY (passenger_id) REFERENCES passengers (id)" +
                    ");";
}
