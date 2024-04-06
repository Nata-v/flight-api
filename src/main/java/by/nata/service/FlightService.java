package by.nata.service;

import by.nata.dto.FlightDto;
import by.nata.dto.FlightFilterDto;
import by.nata.entity.Flight;

import by.nata.mapper.MapperUtils;
import by.nata.repository.FlightDao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static by.nata.mapper.MapperUtils.mapToEntityFlight;
import static by.nata.mapper.MapperUtils.mapToFlightDto;

public class FlightService {
    private static final FlightService INSTANCE = new FlightService();
    private final FlightDao flightDao = FlightDao.getInstance();


    public List<FlightFilterDto> findAll() {
        return flightDao.findAll().stream().map(flight ->
                new FlightFilterDto(flight.getId(), "%s -%s - %s".formatted(
                        flight.getArrivalAirportCode(),
                        flight.getDepartureAirportCode(),
                        flight.getStatus()
                ))).collect(Collectors.toList());
    }

    public Optional<FlightDto> findById(Long id) {
        Optional<Flight> flightOptional = flightDao.findById(id);
        return flightOptional.map(MapperUtils::mapToFlightDto);
    }

    public boolean delete(Long id) {
        Optional<Flight> flightOptional = flightDao.findById(id);
        if (flightOptional.isPresent()) {
            Flight flight = flightOptional.get();
            boolean deleted = flightDao.delete(flight.getId());
            if (deleted) {
                return true;
            }

        }
        return false;
    }
    public FlightDto save(FlightDto flightDto) {
        Flight flight = mapToEntityFlight(flightDto);
        Flight savedFlight = flightDao.save(flight);
        return mapToFlightDto(savedFlight);
    }
    public FlightDto update(FlightDto flightDto) {
        Flight flight = mapToEntityFlight(flightDto);
        boolean updated = flightDao.update(flight);
        if (updated) {
            return mapToFlightDto(flight);
        } else {
            throw new RuntimeException("Failed to update flight.");
        }
    }


        public static FlightService getInstance () {
            return INSTANCE;
        }

    private FlightService() {
        }



    }
