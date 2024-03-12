package by.nata.repository;

import by.nata.config.JdbcUtil;
import by.nata.entity.Flight;
import by.nata.entity.FlightStatus;
import by.nata.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightDao implements Dao<Long, Flight>{
    private final static FlightDao INSTANCE = new FlightDao();
    private final static String SAVE_SQL = """
                INSERT INTO flight 
                (flight_no, departure_date, departure_airport_code, arrival_date, arrival_airport_code,  aircraft_id, status) 
                VALUES(?,?,?,?,?,?,?)""";
    private final static String FIND_ALL_SQL = """
            SELECT id,flight_no, departure_date, 
            departure_airport_code, arrival_date, arrival_airport_code, aircraft_id, status 
            FROM flight
            """;

    private final static String UPDATE_SQL = """
                UPDATE flight SET flight_no = ?, departure_date = ?, departure_airport_code = ?, arrival_date =?, 
                arrival_airport_code = ?,  aircraft_id = ?, status = ? WHERE id = ?
                """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + " WHERE id = ?";
    private static final String DELETE_SQL = """
              DELETE FROM flight WHERE id = ?
            """;
    @Override
    public boolean update(Flight flight) {
        String departureDateStr = flight.getDepartureDate().toString();
        String arrivalDateStr = flight.getDepartureDate().toString();
        try(var connection = JdbcUtil.getConnection();
            var statement = connection.prepareStatement(UPDATE_SQL)){

            statement.setString(1, flight.getFlightNo());
            statement.setString(2, departureDateStr);
            statement.setString(3, flight.getDepartureAirportCode());
            statement.setString(4, arrivalDateStr);
            statement.setString(5, flight.getArrivalAirportCode());
            statement.setInt(6, flight.getAircraftId());
            statement.setString(7, flight.getStatus().name());
            statement.setLong(8, flight.getId());

            return statement.executeUpdate() > 0;
        }catch (SQLException e){
            throw new DaoException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Flight> findAll() {

        try(var connection = JdbcUtil.getConnection();
            var statement = connection.prepareStatement(FIND_ALL_SQL)){
            List<Flight> flights = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next()){
                flights.add(buildFlight(result));
            }
            return flights;
        }catch (SQLException | ClassNotFoundException e){
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Flight> findById(Long id ) {
        try (var connection = JdbcUtil.getConnection()) {
            return findById(id, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private Flight buildFlight(ResultSet result) throws SQLException {
        return  new Flight(result.getLong("id"),
                result.getString("flight_no"),
                result.getTimestamp("departure_date").toLocalDateTime(),
                result.getString("departure_airport_code"),
                result.getTimestamp("arrival_date").toLocalDateTime(),
                result.getString("arrival_airport_code"),
                result.getInt("aircraft_id"),
                FlightStatus.valueOf(result.getString("status").toUpperCase()));
    }


    public Optional<Flight> findById(Long id, Connection connection) {
        try(var statement = connection.prepareStatement(FIND_BY_ID_SQL)){
            statement.setLong(1,id);
            var result = statement.executeQuery();
            Flight flight = null;

            if (result.next()) {
                flight = buildFlight(result);
            }
            return Optional.ofNullable(flight);

        }catch (SQLException e){
            throw  new DaoException(e);
        }
    }

    @Override
    public Flight save(Flight flight) {
        try(var connection = JdbcUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, flight.getFlightNo());
            statement.setString(2, flight.getDepartureDate().toString());
            statement.setString(3, flight.getDepartureAirportCode());
            statement.setString(4, flight.getArrivalDate().toString());
            statement.setString(5, flight.getArrivalAirportCode());
            statement.setInt(6, flight.getAircraftId());
            statement.setObject(7, flight.getStatus().name());


            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()){
                 flight.setId(keys.getLong(1));
            }
            return flight;
        }catch (SQLException e){
            throw new DaoException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try(var connection = JdbcUtil.getConnection();
            var statement = connection.prepareStatement(DELETE_SQL)){

            statement.setLong(1,id);

            return statement.executeUpdate() > 0;
        }catch (SQLException e){
            throw new DaoException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private FlightDao() {
    }
    public static FlightDao getInstance(){
        return INSTANCE;
    }
}
