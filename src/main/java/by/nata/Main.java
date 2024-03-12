package by.nata;

import by.nata.dto.TicketFilter;
import by.nata.entity.Flight;
import by.nata.entity.FlightStatus;
import by.nata.entity.Ticket;
import by.nata.repository.FlightDao;
import by.nata.repository.TicketDao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws SQLException {
        var flightDao = FlightDao.getInstance();
        //  System.out.println(flightDao.delete(10L));
        LocalDateTime departureDateTime = LocalDateTime.of(2023, 1, 15, 12, 0);


//        Flight flight = new Flight();
//        flight.setFlightNo("11111");
//       // flight.setDepartureDate(LocalDateTime.now());
//        flight.setDepartureDate(departureDateTime);
//        flight.setDepartureAirportCode("CDG");
//        flight.setArrivalDate(LocalDateTime.now());
//        flight.setArrivalAirportCode("MXP");
//        flight.setAircraftId(1);
//        flight.setStatus(FlightStatus.ARRIVED);
//        System.out.println(flightDao.save(flight));
//       // System.out.println(flightDao.findById(1L));

//        Optional<Flight> ticketOptional = flightDao.findById(1L); // Предполагается, что ID билета существует в базе данных
//
//        // Проверяем, найден ли билет
//        if (ticketOptional.isPresent()) {
//            Flight flight = ticketOptional.get();
//
           var ticketDao = TicketDao.getInstance();
//            Ticket ticket = new Ticket();
//            ticket.setPassportNo("KB2525");
//            ticket.setPassengerName("Ivan Ivanov");
//            // Flight flight = new Flight();
//            ticket.setFlight(flight);
//            ticket.setSeatNo("A2");
//            ticket.setCost(BigDecimal.TEN);
//            System.out.println(ticketDao.save(ticket));


//
//        // Получаем билет из базы данных по его ID
//        Optional<Flight> ticketOptional = flightDao.findById(2L); // Предполагается, что ID билета существует в базе данных
//
//        // Проверяем, найден ли билет
//        if (ticketOptional.isPresent()) {
//            Flight flightUpdater = ticketOptional.get();
//            // Модифицируем некоторые данные в билете
////            ticketToUpdate.setPassportNo("NEW_PASSPORT_NO");
//            flightUpdater.setStatus(FlightStatus.CANCELLED);
//
//            // Вызываем метод update для обновления данных в базе
//            boolean updated = flightDao.update(flightUpdater);
//
//            // Проверяем результат обновления
//            if (updated) {
//                System.out.println("Полет успешно обновлен.");
//            } else {
//                System.out.println("Ошибка при обновлении полета.");
//            }
//        } else {
//            System.out.println("Полет с указанным ID не найден в базе данных.");
//        }


//        // Получаем билет из базы данных по его ID
//        Optional<Ticket> ticketOptional = ticketDao.findById(2L); // Предполагается, что ID билета существует в базе данных
//
//        // Проверяем, найден ли билет
//        if (ticketOptional.isPresent()) {
//            Ticket ticketToUpdate = ticketOptional.get();
//            // Модифицируем некоторые данные в билете
////            ticketToUpdate.setPassportNo("NEW_PASSPORT_NO");
//            ticketToUpdate.setCost(BigDecimal.valueOf(700));
//
//            // Вызываем метод update для обновления данных в базе
//            boolean updated = ticketDao.update(ticketToUpdate);
//
//            // Проверяем результат обновления
//            if (updated) {
//                System.out.println("Билет успешно обновлен.");
//            } else {
//                System.out.println("Ошибка при обновлении билета.");
//            }
//        } else {
//            System.out.println("Билет с указанным ID не найден в базе данных.");
//        }


        // Создание объекта фильтра
        TicketFilter filter = new TicketFilter(null, "A2", 5, 0);
//        filter.setPassengerName("John Doe"); // Устанавливаем имя пассажира
//        filter.setSeatNo("A1"); // Устанавливаем номер места
//        filter.setLimit(10); // Устанавливаем лимит записей
//        filter.setOffset(0); // Устанавливаем смещение

        // Вызов метода findAll и вывод результатов
        List<Ticket> tickets = ticketDao.findAll(filter);
        for (Ticket ticket : tickets) {
            System.out.println(ticket); // Вывод информации о найденных билетах
        }
        }


    }

