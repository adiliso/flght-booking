package az.edu.turing.model.dto.constants;

import java.time.format.DateTimeFormatter;

public class AppConstants {

    public static final DateTimeFormatter FLIGHT_DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    public static final DateTimeFormatter FLIGHT_TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
}
