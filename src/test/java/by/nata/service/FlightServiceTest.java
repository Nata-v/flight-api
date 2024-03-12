package by.nata.service;

import by.nata.config.JdbcUtilTest;
import by.nata.dto.FlightDto;
import by.nata.dto.FlightFilterDto;
import by.nata.entity.Flight;
import by.nata.entity.FlightStatus;
import by.nata.repository.FlightDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class FlightServiceTest {
    Connection connection;
    @Mock
    private FlightDao flightDao;

    @InjectMocks
    private FlightService flightService;


    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        MockitoAnnotations.openMocks(this);
        connection = JdbcUtilTest.getConnection();
        // if (connection != null) {
//            connection.createStatement().executeUpdate("delete from flight");
//            connection.createStatement().executeUpdate("delete from ticket");
        // }
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }


    @Test
    public void testFindById() {
        Long flightId = 2L;
        Flight mockFlight = new Flight();
        mockFlight.setId(flightId);
        when(flightDao.findById(flightId)).thenReturn(Optional.of(mockFlight));

        Optional<FlightDto> result = flightService.findById(flightId);

        assertTrue(result.isPresent());
        assertEquals(flightId, result.get().getId());
    }

    @Test
    public void testDelete() {
        Long flightId = 2L;
        Flight mockFlight = new Flight();
        mockFlight.setId(flightId);
        when(flightDao.findById(flightId)).thenReturn(Optional.of(mockFlight));
        when(flightDao.delete(flightId)).thenReturn(true);

        boolean result = flightService.delete(flightId);

        assertTrue(result);
        verify(flightDao, times(1)).findById(flightId);
        verify(flightDao, times(1)).delete(flightId);
    }

//        @Test
//    public void testSave() {
//        FlightDto flightDto = new FlightDto();
//        Flight mockFlight = new Flight();
//        mockFlight.setDepartureDate(LocalDateTime.now());
//        when(flightDao.save(any(Flight.class))).thenReturn(mockFlight);
//
//        FlightDto result = flightService.save(flightDto);
//
//        assertNotNull(result);
//
//    }
//
//    @Test
//    public void testUpdate() {
//        FlightDto flightDto = new FlightDto();
//        Flight mockFlight = new Flight();
//        when(flightDao.update(any(Flight.class))).thenReturn(true);
//
//        FlightDto result = flightService.update(flightDto);
//
//        assertNotNull(result);
//
//
//    }
}