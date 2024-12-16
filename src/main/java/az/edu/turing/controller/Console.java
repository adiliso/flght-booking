package az.edu.turing.controller;

import az.edu.turing.domain.dao.abstracts.BookingDao;
import az.edu.turing.domain.dao.abstracts.FlightDao;
import az.edu.turing.domain.dao.abstracts.PassengerDao;
import az.edu.turing.domain.dao.impl.postgres.BookingDaoPostgres;
import az.edu.turing.domain.dao.impl.postgres.FlightDaoPostgres;
import az.edu.turing.domain.dao.impl.postgres.PassengerDaoPostgres;
import az.edu.turing.model.dto.constants.AppConstants;
import az.edu.turing.model.dto.request.BookingCreateRequest;
import az.edu.turing.model.dto.request.FlightCreateRequest;
import az.edu.turing.model.dto.request.FlightSearchRequest;
import az.edu.turing.model.dto.response.BookingResponse;
import az.edu.turing.model.dto.response.FlightResponse;
import az.edu.turing.service.BookingService;
import az.edu.turing.service.FlightService;
import az.edu.turing.service.PassengerService;
import az.edu.turing.service.impl.BookingServiceImpl;
import az.edu.turing.service.impl.FlightServiceService;
import az.edu.turing.service.impl.PassengerServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Console {

    private static final PassengerDao passengerDao =
//            new PassengerDaoInMemory();
//            new PassengerFileDao();
            new PassengerDaoPostgres();

    private static final FlightDao flightDao =
//            new FlightDaoInMemory();
//            new FlightFileDao();
            new FlightDaoPostgres();

    private static final BookingDao bookingDao =
//            new BookingDaoInMemory();
//            new BookingFileDao();
            new BookingDaoPostgres();

    private static final PassengerService passengerService = new PassengerServiceImpl(passengerDao);
    private static final FlightService flightService = new FlightServiceService(flightDao);
    private static final BookingService bookingService = new BookingServiceImpl(bookingDao);

    private static final PassengerController passengerController = new PassengerController(passengerService);
    private static final FlightController flightController = new FlightController(flightService);
    private static final BookingController bookingController = new BookingController(bookingService);

    public void run() {
//        createFlights();
        showCommandList();
        while (true) {
            int command = getValidInt("Enter a command: ");
            switch (command) {
                case 0:
                    saveChanges();
                    return;
                case 1:
                    showOnlineBoard();
                    break;
                case 2:
                    showFlightInfo();
                    break;
                case 3:
                    searchAndBookFlight();
                    break;
                case 4:
                    cancelBooking();
                    break;
                case 5:
                    showMyFlights();
                    break;
                default:
                    System.out.println("Please enter a valid command");
            }
        }
    }

    private void saveChanges() {
        passengerDao.saveChanges();
        flightDao.saveChanges();
        bookingDao.saveChanges();
    }

    private void showMyFlights() {
        String[] fullName = getString("Enter your full name: ").split(" ");
        String name = fullName[0];
        String lastName = fullName[1];
        Set<BookingResponse> response = bookingController.findAllByPassengerNameAndLastName(name, lastName);
        printBookingHeader();
        response.forEach(System.out::println);
    }

    private void printBookingHeader() {
        System.out.printf("%-15s%-15s%-30s%-30s%-15s\n",
                "ID", "Flight ID", "Created By", "passengers", "is active");
    }

    private void cancelBooking() {
        long bookingId = getValidLong("Enter booking ID: ");
        try {
            if (bookingController.cancel(bookingId)) {
                System.out.println("Booking cancelled");
            }
            System.out.println("Booking is not active.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void searchAndBookFlight() {
        searchFlight();
        long flightId = getValidLong("Enter flight id or press '0' to return main menu: ");
        if (flightId == 0) return;
        book(flightId);
    }

    private void book(long flightId) {
        String[] createdBy = getString("Enter your name and surname: ").split(" ");
        int numberOfPassengers = getValidInt("Enter number of passengers: ");
        List<String[]> passengers = new ArrayList<>(numberOfPassengers);
        for (int i = 0; i < numberOfPassengers; i++) {
            passengers.add(getString("Enter passenger name and surname: ").split(" "));
        }
        bookingController.create(new BookingCreateRequest(
                flightId,
                createdBy,
                passengers
        ));
        System.out.println("Booking created");
    }

    private void searchFlight() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter destination point: ");
        String destinationPoint = sc.nextLine();
        int day = getValidInt("Enter the day: ");
        int month = getValidInt("Enter the month: ");
        int year = getValidInt("Enter the year: ");
        int numberOfPassengers = getValidInt("Enter the number of passengers: ");
        LocalDate date = LocalDate.of(year, month, day);
        Set<FlightResponse> flights = flightController.search(new FlightSearchRequest(
                destinationPoint, date, numberOfPassengers));
        if (flights.isEmpty()) System.out.println("No flights found");
        else displayFlights(flights);
    }

    private void showFlightInfo() {
        while (true) {
            long flightId = getValidLong("Enter a flight ID: ");
            try {
                FlightResponse flight = flightController.showInfo(flightId);
                printFlightHeader();
                displayFlight(flight);
                return;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String getString(String message) {
        Scanner sc = new Scanner(System.in);
        System.out.print(message);
        return sc.nextLine();
    }

    private int getValidInt(String message) {
        System.out.print(message);
        Scanner sc = new Scanner(System.in);
        while (true) {
            if (sc.hasNextLong()) {
                return sc.nextInt();
            }
            sc.nextLine();
            System.out.println("Please enter a number!");
        }
    }

    private long getValidLong(String message) {
        System.out.print(message);
        Scanner sc = new Scanner(System.in);
        while (true) {
            if (sc.hasNextLong()) {
                return sc.nextLong();
            }
            sc.nextLine();
            System.out.println("Please enter a number!");
        }
    }

    private void showCommandList() {
        System.out.println("0 --> exit the program\n" +
                "1 --> show online board\n" +
                "2 --> flight info\n" +
                "3 --> search and book a flight\n" +
                "4 --> cancel the booking\n" +
                "5 --> my flights"
        );
    }

    private void showOnlineBoard() {
        Set<FlightResponse> flights = flightController.findAllInNext24Hours();
        System.out.println("Flights in next 24 hours:");
        displayFlights(flights);
    }

    private void displayFlights(Set<FlightResponse> flights) {
        printFlightHeader();
        for (FlightResponse flight : flights) {
            displayFlight(flight);
        }
    }

    private void printFlightHeader() {
        System.out.printf("%-15s%-15s%-15s%-15s%-15s\n", "Id", "Date", "Time", "To", "Free seats");
    }

    private void displayFlight(FlightResponse flight) {
        System.out.printf("%-15s%-15s%-15s%-15s%-15s\n",
                flight.getId(),
                flight.getDepartureDate().format(AppConstants.FLIGHT_DATE_FORMAT),
                flight.getDepartureTime().format(AppConstants.FLIGHT_TIME_FORMAT),
                flight.getDestinationPoint(),
                flight.getFreeSeats());
    }

    private void createFlights() {
        LocalDateTime dateTime = LocalDateTime.now();
        List<FlightCreateRequest> flights = new ArrayList<>(Arrays.asList(new FlightCreateRequest[]{
                new FlightCreateRequest(dateTime.plusHours(10), "Istanbul", 200),
                new FlightCreateRequest(dateTime.plusHours(15), "Baku", 150),
                new FlightCreateRequest(dateTime.plusHours(3), "Manchester", 300),
                new FlightCreateRequest(dateTime.plusHours(1), "Madeira", 100),
                new FlightCreateRequest(dateTime.plusHours(46), "New York", 150),
                new FlightCreateRequest(dateTime.plusHours(32), "London", 150),
                new FlightCreateRequest(dateTime.plusHours(12), "Moscow", 200),
                new FlightCreateRequest(dateTime.plusHours(55), "Paris", 100)
        }));
        flights.forEach(flightController::create);
    }
}
