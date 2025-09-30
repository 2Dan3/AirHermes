package com.ftn.dan.airhermes.controller;

import com.ftn.dan.airhermes.model.dto.ReportDTO;
import com.ftn.dan.airhermes.model.entity.*;
import com.ftn.dan.airhermes.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/flights")
public class FlightsController {
    public static final String FLIGHT_KEY = "flight";

    @Autowired
    private FlightService flightService;
    @Autowired
    private AirplaneService airplaneService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private AirportService airportService;
    @Autowired
    private DiscountService discountService;

    @Autowired
    private ServletContext servletContext;
    private String baseURL;

    @PostConstruct
    public void init() { baseURL = servletContext.getContextPath() + "/";}

    @GetMapping
    public ModelAndView index(
            @RequestParam(required = false) Long flight_id,
            @RequestParam(required = false, name = "departureTimestamp")
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime rawParamDepartureDateTime,
            @RequestParam(required = false) String departureAirportOrCityOrStateSearchTerm,
            @RequestParam(required = false) String destinationAirportOrCityOrStateSearchTerm,
            @RequestParam(required = false) Integer passengers,
            @RequestParam(required = false) Boolean lookForSimilarTimingFlights,
            @RequestParam(defaultValue = "departure_timestamp-asc", name = "sort") String sortAndDirection,
            HttpSession httpSession, HttpServletResponse response) throws IOException {

//        if (departureTimestamp!=null && departureTimestamp.trim().equals(""))
//            departureTimestamp = null;

        Timestamp departureTimestamp = null;
        if (rawParamDepartureDateTime != null)
            departureTimestamp = Timestamp.valueOf(rawParamDepartureDateTime);

        if ( flight_id!=null && (flight_id.equals(0L) || flight_id.equals(0)) )
            flight_id = null;

        if (passengers!=null && passengers.equals(0))
            passengers = null;

        if ( lookForSimilarTimingFlights == null || Boolean.FALSE.equals(lookForSimilarTimingFlights) || departureTimestamp==null )
            lookForSimilarTimingFlights = false;

        if (departureAirportOrCityOrStateSearchTerm!=null && departureAirportOrCityOrStateSearchTerm.trim().equals(""))
            departureAirportOrCityOrStateSearchTerm = null;

        if (destinationAirportOrCityOrStateSearchTerm!=null && destinationAirportOrCityOrStateSearchTerm.trim().equals(""))
            destinationAirportOrCityOrStateSearchTerm = null;

        List<Flight> flights = flightService.find(flight_id, departureTimestamp, departureAirportOrCityOrStateSearchTerm, destinationAirportOrCityOrStateSearchTerm, passengers, lookForSimilarTimingFlights, sortAndDirection.replace('-', ' '));

        ModelAndView responsePage = new ModelAndView("flights");
        responsePage.addObject("flights", flights);
        responsePage.addObject("sort", sortAndDirection);
        return responsePage;
    }

    @GetMapping(value = "/advancedSearch")
    public ModelAndView getByID(
            @RequestParam(required = false) Long flight_id,
            HttpSession httpSession, HttpServletResponse response) throws IOException {

        User loggedUser = (User) httpSession.getAttribute(UsersController.USER_KEY);
        if (loggedUser == null || !loggedUser.isAdmin()) {
            response.sendRedirect(baseURL + "flights");
            return null;
        }
        List<Flight> flights = flightService.findAllBy(new Long[]{flight_id});

        ModelAndView responsePage = new ModelAndView("flights");
        responsePage.addObject("flights", flights);
        return responsePage;
    }

    @GetMapping(value = "/report")
    public ModelAndView getRevenueReportForInterval(
            @RequestParam(required = false, name = "timestampMin")
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime rawParamLocalDateTimeMin,
            @RequestParam(required = false, name = "timestampMax")
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime rawParamLocalDateTimeMax,
            @RequestParam(defaultValue = "flight_id-asc", name = "sort") String sortAndDirection,
            HttpSession httpSession, HttpServletResponse response) throws IOException {

        User loggedUser = (User) httpSession.getAttribute(UsersController.USER_KEY);
        if (loggedUser == null || !loggedUser.isAdmin()) {
            response.sendRedirect(baseURL + "flights");
            return null;
        }

        Timestamp timestampMin = null;
        if (rawParamLocalDateTimeMin != null)
            timestampMin = Timestamp.valueOf(rawParamLocalDateTimeMin);

        Timestamp timestampMax = null;
        if(rawParamLocalDateTimeMax != null)
            timestampMax = Timestamp.valueOf(rawParamLocalDateTimeMax);

        ModelAndView responsePage = new ModelAndView("report");

        if (timestampMin == null && timestampMax == null)
            return responsePage;

        List<ReportDTO> bookedFlights = flightService.findFlightsAndRevenueForInterval(timestampMin, timestampMax, sortAndDirection.replace('-', ' '));

        Long totalTicketsSold = 0L;
        Double totalRevenue = 0.0;
        for (ReportDTO bookedFlight : bookedFlights) {
            totalTicketsSold += bookedFlight.getSeatsSold();
            totalRevenue += bookedFlight.getFlightRevenue();
        }

        responsePage.addObject("flights", bookedFlights);
        responsePage.addObject("totalTicketsSold", totalTicketsSold);
        responsePage.addObject("totalRevenue", totalRevenue);
        return responsePage;
    }

    @GetMapping(value = "/cancel")
    public ModelAndView getFlightCancellationForm(
            @RequestParam(name = "flightId") Long flightID,
            HttpSession session, HttpServletResponse response) throws IOException {

        Flight flight;
        if (flightID == null || (flight = flightService.findAllBy(new Long[]{flightID}).get(0)) == null) {
            response.sendRedirect(baseURL + "flights");
            return null;
        }

        ModelAndView mov = new ModelAndView("cancelFlight");
        mov.addObject("flight", flight);
        return mov;
    }

    @PostMapping(value = "/cancel")
    public ModelAndView cancelFlight(
            @RequestParam(name = "flightId") Long flightID,
            @RequestParam(name = "reason") String reasonOfCancellation,
            HttpSession session, HttpServletResponse response) throws IOException {

        ModelAndView mov = new ModelAndView("cancelFlight");

        Flight flight;
        if (flightID == null || (flight = flightService.findAllBy(new Long[]{flightID}).get(0)) == null) {
            response.sendRedirect(baseURL + "flights");
            return null;
        }

        try {
            flightService.cancelFlight(flight, reasonOfCancellation);
            response.sendRedirect(baseURL + "flights");
            return null;
        }catch (Exception e) {
            mov.addObject("error", e.getMessage());
            mov.addObject("flight", flight);
            return mov;
        }
    }

    @GetMapping(value = "/setup")
    public ModelAndView setupFlightPage(
            @RequestParam(required = false, name = "flightId") Long flightID,
            HttpSession session, HttpServletResponse response) throws IOException {

        User loggedUser = (User) session.getAttribute(UsersController.USER_KEY);
        if (loggedUser == null || !loggedUser.isAdmin()) {
            response.sendRedirect(baseURL + "flights");
            return null;
        }

        ModelAndView mov = new ModelAndView("setupFlight");

        Flight flight;
        if (flightID != null && (flight = flightService.findAllBy(new Long[]{flightID}).get(0)) != null) {
            mov.addObject("flight", flight);
        }

        List<Location> allLocations = locationService.findAll();
        List<Airport> allAirports = airportService.findAll();
        List<Airplane> allAirplanes = airplaneService.findAll();
        List<DiscountStandard> allDiscounts = discountService.findAll();

        mov.addObject("allLocations", allLocations);
        mov.addObject("airports", allAirports);
        mov.addObject("airplanes", allAirplanes);
        mov.addObject("discounts", allDiscounts);

        return mov;
    }

    @PostMapping(value = "/setup")
    public void setupFlightCreateEdit(
            @RequestParam(name = "flightId") Long flightID,
            @RequestParam(name = "airportDeparture") String airportDepartureCodeName,
            @RequestParam(name = "airportDestination") String airportDestinationCodeName,
            @RequestParam(name = "airplaneId") Long airplaneID,
            @RequestParam(name = "departureTimestamp")
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime departureLocalDateTime,
            @RequestParam(name = "flightDurationMinutes") String rawflightDurationMinutes,
            @RequestParam(name = "flightTicketPrice") Integer flightTicketPrice,
            @RequestParam(name = "discountStandardId") Long discountStandardID,

            HttpSession session, HttpServletResponse response) throws IOException {

        User loggedUser = (User) session.getAttribute(UsersController.USER_KEY);
        if (loggedUser == null || !loggedUser.isAdmin()) {
            response.sendRedirect(baseURL + "flights");
            return;
        }

        Integer flightDurationMinutes = flightService.parseMinutes(rawflightDurationMinutes);

        List<Flight> flight = flightService.findAllBy(new Long[]{flightID});
        if (!flight.isEmpty())
            flightService.updateFlight(flight.get(0), airportDepartureCodeName, airportDestinationCodeName, airplaneID, departureLocalDateTime, flightDurationMinutes, flightTicketPrice, discountStandardID);
        else
            flightService.save(flightID, airportDepartureCodeName, airportDestinationCodeName, airplaneID, departureLocalDateTime, flightDurationMinutes, flightTicketPrice, discountStandardID);

        response.sendRedirect(baseURL + "flights");
    }

    @PostMapping(value = "/delete")
    public void deleteFlight(
            @RequestParam(name = "flightId") Long flightID,
            HttpSession session, HttpServletResponse response) throws IOException {

        User loggedUser = (User) session.getAttribute(UsersController.USER_KEY);
        if (loggedUser == null || !loggedUser.isAdmin()) {
            response.sendRedirect(baseURL + "flights");
            return;
        }

        Flight flight = flightService.findAllBy(new Long[]{flightID}).get(0);
        if (flight == null)
            return;

        flightService.deleteFlight(flight);
        response.sendRedirect(baseURL + "flights");
    }

}
