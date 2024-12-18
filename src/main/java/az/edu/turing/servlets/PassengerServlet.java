package az.edu.turing.servlets;

import az.edu.turing.controller.PassengerController;
import az.edu.turing.model.dto.PassengerDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class PassengerServlet extends HttpServlet {

    private final PassengerController controller;
    private final ObjectMapper objectMapper;

    public PassengerServlet(PassengerController controller) {
        this.controller = controller;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!"application/json".equalsIgnoreCase(req.getContentType())) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid Content-Type, expected application/json");
            return;
        }

        try {
            Map jsonBody = objectMapper.readValue(req.getInputStream(), Map.class);

            String firstName = (String) jsonBody.get("firstName");
            String lastName = (String) jsonBody.get("lastName");

            controller.create(new PassengerDto(firstName, lastName));

            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(objectMapper.writeValueAsString(("message"+ "Data received successfully")));

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid JSON format");
            e.printStackTrace();
        }
    }
}
