package by.nata.dto;


import java.math.BigDecimal;

public class TicketDto {
    private Long id;
    private String passportNo;
    private String passengerName;
    private FlightDto flight;
    private String seatNo;
    private BigDecimal cost;

    public TicketDto(Long id, String passportNo, String passengerName, FlightDto flight, String seatNo, BigDecimal cost) {
        this.id = id;
        this.passportNo = passportNo;
        this.passengerName = passengerName;
        this.flight = flight;
        this.seatNo = seatNo;
        this.cost = cost;
    }

    public TicketDto() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public FlightDto getFlight() {
        return flight;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public BigDecimal getCost() {
        return cost;
    }
}
