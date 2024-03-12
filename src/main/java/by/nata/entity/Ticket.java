package by.nata.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Ticket {
    private Long id;
    private String passportNo;
    private String passengerName;
    private Flight flight;
    private String seatNo;
    private BigDecimal cost;

    public Ticket() {
    }

    public Ticket(Long id, String passportNo, String passengerName, Flight flight, String seatNo, BigDecimal cost) {
        this.id = id;
        this.passportNo = passportNo;
        this.passengerName = passengerName;
        this.flight = flight;
        this.seatNo = seatNo;
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", passportNo='" + passportNo + '\'' +
                ", passengerName='" + passengerName + '\'' +
                ", flight=" + flight +
                ", seatNo='" + seatNo + '\'' +
                ", cost=" + cost +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Ticket ticket)) return false;
        return Objects.equals(getId(), ticket.getId()) && Objects.equals(getPassportNo(), ticket.getPassportNo()) && Objects.equals(getPassengerName(), ticket.getPassengerName()) && Objects.equals(getFlight(), ticket.getFlight()) && Objects.equals(getSeatNo(), ticket.getSeatNo()) && Objects.equals(getCost(), ticket.getCost());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPassportNo(), getPassengerName(), getFlight(), getSeatNo(), getCost());
    }
}
