package by.nata.service;

import by.nata.dto.FlightDto;
import by.nata.dto.TicketDto;
import by.nata.dto.TicketFilterDto;
import by.nata.entity.Flight;
import by.nata.entity.Ticket;
import by.nata.mapper.MapperUtils;
import by.nata.repository.TicketDao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static by.nata.mapper.MapperUtils.*;

public class TicketService {
    private final static TicketService INSTANCE = new TicketService();
    private final TicketDao ticketDao = TicketDao.getInstance();

    public List<TicketFilterDto> findAllByFlightId (Long flightId){
        return ticketDao.findAllByFlightId(flightId).stream().map(
                ticket -> new TicketFilterDto(ticket.getId(), ticket.getFlight().getId(),ticket.getSeatNo())
        ).collect(Collectors.toList());
    }

    public List<TicketDto> findAll() {
        List<Ticket> tickets = ticketDao.findAll();
        return tickets.stream()
                .map(ticket -> new TicketDto(ticket.getId(),
                        ticket.getPassportNo(),
                        ticket.getPassengerName(),
                        mapToFlightDto(ticket.getFlight()),
                        ticket.getSeatNo(),
                        ticket.getCost()))
                .collect(Collectors.toList());
    }
    public Optional<TicketDto> findById(Long id) {
        Optional<Ticket> ticketOptional = ticketDao.findById(id);
        return ticketOptional.map(MapperUtils::mapToTicketDto);
    }

    public boolean delete(Long id) {
        Optional<Ticket> ticketOptional = ticketDao.findById(id);
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            boolean deletedTicket = ticketDao.delete(ticket.getId());
            if (deletedTicket) {
                return true;
            }
        }
        return false;
    }
    public TicketDto save(TicketDto ticketDto) {
       Ticket ticket = mapToEntityTicket(ticketDto);
        Ticket savedTicket = ticketDao.save(ticket);
        return mapToTicketDto(savedTicket);
    }
    public TicketDto update(TicketDto ticketDto) {
        Ticket ticket = mapToEntityTicket(ticketDto);
        boolean updatedTicket = ticketDao.update(ticket);
        if (updatedTicket) {
            return mapToTicketDto(ticket);
        } else {
            throw new RuntimeException("Failed to update ticket.");
        }
    }




    public static TicketService getInstance(){
        return INSTANCE;
    }
    private TicketService() {
    }
}
