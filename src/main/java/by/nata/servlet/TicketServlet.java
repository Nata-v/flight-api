package by.nata.servlet;

import by.nata.dto.TicketDto;
import by.nata.mapper.JsonUtils;
import by.nata.service.TicketService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.BufferedReader;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@WebServlet({"/tickets", "/tickets-by-flight", "/tickets/*"})
public class TicketServlet extends HttpServlet {
    private final TicketService ticketService = TicketService.getInstance();



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String requestURI = req.getRequestURI();
        String[] parts = requestURI.split("/");
        String lastPart = parts[parts.length - 1]; // Получаем последнюю часть URI

        if (lastPart.equals("tickets")) {
            handleFindAll(req, resp);
        } else if (lastPart.equals("tickets-by-flight")) {
            handleFindAllByFlightId(req, resp);
        } else {
            Long ticketId;
            try {
                ticketId = Long.parseLong(lastPart);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("404 - Not Found");
                return;
            }
            handleFindById(ticketId, req, resp);
        }
    }

    private void handleFindById(Long id, HttpServletRequest req, HttpServletResponse resp) throws IOException {


        Optional<TicketDto> ticketDtoOptional = ticketService.findById(id);
        if (ticketDtoOptional.isPresent()) {
            TicketDto ticketDto = ticketDtoOptional.get();
            String json = JsonUtils.mapToTicketJson(ticketDto);
            resp.getWriter().write(json);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Ticket not found");
        }
    }

    private void handleFindAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<TicketDto> ticketDtoList = ticketService.findAll();
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[");
        ticketDtoList.forEach(ticketDto -> {
            jsonBuilder.append(JsonUtils.mapToTicketJson(ticketDto)).append(",");
        });
        if (!ticketDtoList.isEmpty()) {
            jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        }
        jsonBuilder.append("]");

        resp.getWriter().write(jsonBuilder.toString());
    }

    private void handleFindAllByFlightId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long flightId = Long.valueOf(req.getParameter("flightId"));
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[");
        ticketService.findAllByFlightId(flightId).forEach(ticketDto -> {
            jsonBuilder.append("{");
            jsonBuilder.append("\"id\":").append(ticketDto.id()).append(",");
            jsonBuilder.append("\"flightId\":").append(ticketDto.flightId()).append(",");
            jsonBuilder.append("\"seatNo\":\"").append(ticketDto.seatNo()).append("\"");
            jsonBuilder.append("},");
        });
        if (jsonBuilder.charAt(jsonBuilder.length() - 1) == ',') {
            jsonBuilder.deleteCharAt(jsonBuilder.length() - 1);
        }
        jsonBuilder.append("]");

        resp.getWriter().write(jsonBuilder.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String requestURI = req.getRequestURI();
        String[] parts = requestURI.split("/");

        // Извлекаем ID билета из последней части URI
        String lastPart = parts[parts.length - 1];
        Long ticketId;

        try {
            ticketId = Long.parseLong(lastPart);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("400 - Bad Request: Invalid ticket ID");
            return;
        }

        boolean deleted = ticketService.delete(ticketId);
        if (deleted) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Ticket with ID " + ticketId + " deleted successfully");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Ticket with ID " + ticketId + " not found");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());


        // Получаем данные о билете из запроса
        BufferedReader reader = req.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        String jsonBody = requestBody.toString();

        // Десериализация JSON в объект TicketDto
        TicketDto ticketDto = JsonUtils.deserializeTicketDto(jsonBody);

        TicketDto savedTicketDto = ticketService.save(ticketDto);

        // Отправляем ответ
        String json = JsonUtils.mapToTicketJson(savedTicketDto);
        resp.getWriter().write(json);
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        // Получаем данные о билете из запроса
        BufferedReader reader = req.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        String jsonBody = requestBody.toString();
        // Десериализация JSON в объект TicketDto
        TicketDto ticketDto = JsonUtils.deserializeTicketDto(jsonBody);

        TicketDto updatedTicketDto = ticketService.update(ticketDto);

        // Отправляем ответ
        String json = JsonUtils.mapToTicketJson(updatedTicketDto);
        resp.getWriter().write(json);
    }

}

