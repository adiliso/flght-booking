package az.edu.turing;

import java.time.LocalDateTime;
import java.util.TimeZone;

public class App {

    public static void main(String[] args) throws Exception {
        // Console console = new Console();
        // console.run();
        new JettyServer().start();
    }
}
