package az.edu.turing;

import az.edu.turing.controller.BookingController;
import az.edu.turing.controller.FlightController;
import az.edu.turing.controller.PassengerController;
import az.edu.turing.domain.dao.abstracts.BookingDao;
import az.edu.turing.domain.dao.abstracts.FlightDao;
import az.edu.turing.domain.dao.abstracts.PassengerDao;
import az.edu.turing.domain.dao.impl.postgres.BookingDaoPostgres;
import az.edu.turing.domain.dao.impl.postgres.FlightDaoPostgres;
import az.edu.turing.domain.dao.impl.postgres.PassengerDaoPostgres;
import az.edu.turing.service.BookingService;
import az.edu.turing.service.FlightService;
import az.edu.turing.service.PassengerService;
import az.edu.turing.service.impl.BookingServiceImpl;
import az.edu.turing.service.impl.FlightServiceImpl;
import az.edu.turing.service.impl.PassengerServiceImpl;
import az.edu.turing.servlets.FlightServlet;
import az.edu.turing.servlets.PassengerServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class JettyServer {

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
    private static final FlightService flightService = new FlightServiceImpl(flightDao);
    private static final BookingService bookingService = new BookingServiceImpl(bookingDao);

    private static final PassengerController passengerController = new PassengerController(passengerService);
    private static final FlightController flightController = new FlightController(flightService);
    private static final BookingController bookingController = new BookingController(bookingService);

    public void start() throws Exception {
        final Server server = new Server(8080);

        final ServletContextHandler handler = new ServletContextHandler();
        handler.addServlet(new ServletHolder(new PassengerServlet(passengerController)), "/passengers");
        handler.addServlet(new ServletHolder(new FlightServlet(flightController)), "/flights");

        server.setHandler(handler);
        server.start();
        server.join();
    }
}
