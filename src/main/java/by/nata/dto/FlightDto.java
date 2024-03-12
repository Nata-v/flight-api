package by.nata.dto;

import by.nata.entity.FlightStatus;

import java.time.LocalDateTime;

public class FlightDto{
    private Long id;
    private String flightNo;
    private LocalDateTime departureDate;
    private String departureAirportCode;
    private LocalDateTime arrivalDate;
    private String arrivalAirportCode;
    private Integer aircraftId;
    private FlightStatus status;

    public FlightDto() {
    }

    public FlightDto(Long id, String flightNo, LocalDateTime departureDate, String departureAirportCode, LocalDateTime arrivalDate, String arrivalAirportCode, Integer aircraftId, FlightStatus status) {
        this.id = id;
        this.flightNo = flightNo;
        this.departureDate = departureDate;
        this.departureAirportCode = departureAirportCode;
        this.arrivalDate = arrivalDate;
        this.arrivalAirportCode = arrivalAirportCode;
        this.aircraftId = aircraftId;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public Integer getAircraftId() {
        return aircraftId;
    }

    public FlightStatus getStatus() {
        return status;
    }
}
