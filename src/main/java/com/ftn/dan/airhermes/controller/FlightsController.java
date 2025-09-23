package com.ftn.dan.airhermes.controller;

import com.ftn.dan.airhermes.model.dto.ReportDTO;
import com.ftn.dan.airhermes.model.entity.Flight;
import com.ftn.dan.airhermes.model.entity.User;
import com.ftn.dan.airhermes.service.FlightService;
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

        List<Flight> flights = flightService.find(flight_id, departureTimestamp, departureAirportOrCityOrStateSearchTerm, destinationAirportOrCityOrStateSearchTerm, passengers, lookForSimilarTimingFlights);

        ModelAndView responsePage = new ModelAndView("flights");
        responsePage.addObject("flights", flights);
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

        List<ReportDTO> bookedFlights = flightService.findFlightsAndRevenueForInterval(timestampMin, timestampMax);

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

}
