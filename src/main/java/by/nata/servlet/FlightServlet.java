package by.nata.servlet;

import by.nata.dto.FlightDto;
import by.nata.dto.FlightFilterDto;
import by.nata.dto.TicketDto;
import by.nata.mapper.JsonUtils;
import by.nata.service.FlightService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@WebServlet("/flights/*")
public class FlightServlet extends HttpServlet {
        private final FlightService flightService = FlightService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String requestURI = req.getRequestURI();
        String[] parts = requestURI.split("/");
        String lastPart = parts[parts.length - 1]; // Получаем последнюю часть URI

        if (lastPart.equals("flights")) {
            handleFindAll(req, resp);
        } else {
            Long flightId;
            try {
                flightId = Long.parseLong(lastPart);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("404 - Not Found");
                return;
            }
            handleFindById(flightId, req, resp);
        }
    }
    private void handleFindAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (PrintWriter writer = resp.getWriter()) {
            List<FlightFilterDto> flights = flightService.findAll();
            String json = JsonUtils.convertToJson(flights);
            writer.write(json);
        }
    }
    private void handleFindById(Long id, HttpServletRequest req, HttpServletResponse resp) throws IOException {


        Optional<FlightDto> flightDtoOptional = flightService.findById(id);
        if (flightDtoOptional.isPresent()) {
            FlightDto flightDto = flightDtoOptional.get();
            String json = JsonUtils.mapToFlightJson(flightDto);
            resp.getWriter().write(json);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Flight not found");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String requestURI = req.getRequestURI();
        String[] parts = requestURI.split("/");

        // Извлекаем ID полета из последней части URI
        String lastPart = parts[parts.length - 1];
        Long flightId;

        try {
            flightId = Long.parseLong(lastPart);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("400 - Bad Request: Invalid flight ID");
            return;
        }

        boolean deleted = flightService.delete(flightId);
        if (deleted) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Flight with ID " + flightId + " deleted successfully");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Flight with ID " + flightId + " not found");
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        // Получаем данные о полете из запроса
        BufferedReader reader = req.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        String jsonBody = requestBody.toString();

        // Десериализация JSON в объект FlightDto
        FlightDto flightDto = JsonUtils.deserializeFlightDto(jsonBody);

        FlightDto savedFlightDto = flightService.save(flightDto);

        // Отправляем ответ
        String json = JsonUtils.mapToFlightJson(savedFlightDto);
        resp.getWriter().write(json);
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        // Получаем данные о полете из запроса
        BufferedReader reader = req.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        String jsonBody = requestBody.toString();
        // Десериализация JSON в объект TicketDto
        FlightDto flightDto = JsonUtils.deserializeFlightDto(jsonBody);

       FlightDto updatedFlightDto = flightService.update(flightDto);

        // Отправляем ответ
        String json = JsonUtils.mapToFlightJson(updatedFlightDto);
        resp.getWriter().write(json);
    }


}
