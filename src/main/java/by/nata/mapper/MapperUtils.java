package by.nata.mapper;

import by.nata.dto.FlightDto;
import by.nata.dto.TicketDto;
import by.nata.entity.Flight;
import by.nata.entity.Ticket;

import java.math.BigDecimal;

public class MapperUtils {

    public static TicketDto mapToTicketDto(Ticket ticket) {
        return new TicketDto(ticket.getId(),
                ticket.getPassportNo(),
                ticket.getPassengerName(),
                mapToFlightDto(ticket.getFlight()),
                ticket.getSeatNo(),
                ticket.getCost());
    }
    public static FlightDto mapToFlightDto(Flight flight) {
        return new FlightDto(flight.getId(),
                flight.getFlightNo(),
                flight.getDepartureDate(),
                flight.getDepartureAirportCode(),
                flight.getArrivalDate(),
                flight.getArrivalAirportCode(),
                flight.getAircraftId(),
                flight.getStatus()
        );

    }
    public static Ticket mapToEntityTicket (TicketDto ticketDto) {
        return new Ticket(ticketDto.getId(),
                ticketDto.getPassportNo(),
                ticketDto.getPassengerName(),
                mapToEntityFlight(ticketDto.getFlight()),
                ticketDto.getSeatNo(),
                ticketDto.getCost());
    }

    public static Flight mapToEntityFlight (FlightDto flightDto) {
        return new Flight(flightDto.getId(),
                flightDto.getFlightNo(),
                flightDto.getDepartureDate(),
                flightDto.getDepartureAirportCode(),
                flightDto.getArrivalDate(),
                flightDto.getArrivalAirportCode(),
                flightDto.getAircraftId(),
                flightDto.getStatus());
    }


}
