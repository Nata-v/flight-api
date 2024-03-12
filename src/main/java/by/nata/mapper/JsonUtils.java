package by.nata.mapper;

import by.nata.dto.FlightDto;
import by.nata.dto.FlightFilterDto;
import by.nata.dto.TicketDto;

import by.nata.entity.FlightStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class JsonUtils {
    public static String mapToTicketJson(TicketDto ticketDto) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");
        jsonBuilder.append("\"id\":").append(ticketDto.getId()).append(",");
        jsonBuilder.append("\"passportNo\":\"").append(ticketDto.getPassportNo()).append("\",");
        jsonBuilder.append("\"passengerName\":\"").append(ticketDto.getPassengerName()).append("\",");
        jsonBuilder.append("\"flight\":").append(mapToFlightJson(ticketDto.getFlight())).append(",");
        jsonBuilder.append("\"seatNo\":\"").append(ticketDto.getSeatNo()).append("\",");
        jsonBuilder.append("\"cost\":").append(ticketDto.getCost());
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }

    public static String mapToFlightJson(FlightDto flightDto) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");
        jsonBuilder.append("\"id\":").append(flightDto.getId()).append(",");
        jsonBuilder.append("\"flightNo\":\"").append(flightDto.getFlightNo()).append("\",");
        jsonBuilder.append("\"departureDate\":\"").append(flightDto.getDepartureDate()).append("\",");
        jsonBuilder.append("\"departureAirportCode\":\"").append(flightDto.getDepartureAirportCode()).append("\",");
        jsonBuilder.append("\"arrivalDate\":\"").append(flightDto.getArrivalDate()).append("\",");
        jsonBuilder.append("\"arrivalAirportCode\":\"").append(flightDto.getArrivalAirportCode()).append("\",");
        jsonBuilder.append("\"aircraftId\":").append(flightDto.getAircraftId()).append(",");
        jsonBuilder.append("\"status\":\"").append(flightDto.getStatus()).append("\"");
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }
    public static TicketDto deserializeTicketDto(String jsonBody) {
        try {
            JSONObject jsonObject = new JSONObject(jsonBody);
            Long id = jsonObject.getLong("id");
            String passportNo = jsonObject.getString("passportNo");
            String passengerName = jsonObject.getString("passengerName");

            JSONObject flightObject = jsonObject.getJSONObject("flight");
            Long flightId = flightObject.getLong("id");
            String flightNo = flightObject.getString("flightNo");
            LocalDateTime departureDate = LocalDateTime.parse(flightObject.getString("departureDate"));
            String departureAirportCode = flightObject.getString("departureAirportCode");
            LocalDateTime arrivalDate = LocalDateTime.parse(flightObject.getString("arrivalDate"));
            String arrivalAirportCode = flightObject.getString("arrivalAirportCode");
            Integer aircraftId = flightObject.getInt("aircraftId");
            FlightStatus status = FlightStatus.valueOf(flightObject.getString("status"));


            TicketDto ticketDto = new TicketDto(id, passportNo, passengerName,
                    new FlightDto(flightId, flightNo, departureDate, departureAirportCode, arrivalDate,
                            arrivalAirportCode, aircraftId, status), jsonObject.getString("seatNo"),
                    new BigDecimal(jsonObject.getString("cost")));

            return ticketDto;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static FlightDto deserializeFlightDto(String jsonBody) {
        try {
            JSONObject jsonObject = new JSONObject(jsonBody);
            Long id = jsonObject.getLong("id");
            String flightNo = jsonObject.getString("flightNo");
            LocalDateTime departureDate = LocalDateTime.parse(jsonObject.getString("departureDate"));
            String departureAirportCode = jsonObject.getString("departureAirportCode");
            LocalDateTime arrivalDate = LocalDateTime.parse(jsonObject.getString("arrivalDate"));
            String arrivalAirportCode = jsonObject.getString("arrivalAirportCode");
            Integer aircraftId = jsonObject.getInt("aircraftId");
            FlightStatus status = FlightStatus.valueOf(jsonObject.getString("status"));

            return new FlightDto(id, flightNo, departureDate, departureAirportCode, arrivalDate,
                    arrivalAirportCode, aircraftId, status);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertToJson(List<FlightFilterDto> flights) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        for (int i = 0; i < flights.size(); i++) {
            FlightFilterDto flight = flights.get(i);
            json.append("{")
                    .append("\"id\":").append(flight.id()).append(",")
                    .append("\"departureAirportCode\":\"").append(flight.description()).append("\",")
                    .append("\"arrivalAirportCode\":\"").append(flight.description()).append("\",")
                    .append("\"status\":\"").append(flight.description()).append("\"")
                    .append("}");
            if (i < flights.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }


}
