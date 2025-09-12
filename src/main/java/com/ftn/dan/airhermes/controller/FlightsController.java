package com.ftn.dan.airhermes.controller;

import com.ftn.dan.airhermes.model.entity.Flight;
import com.ftn.dan.airhermes.model.entity.User;
import com.ftn.dan.airhermes.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
//            todo @RequestParam(required = false) Date departureTimestamp,
            @RequestParam(required = false) String departureTimestamp,
            @RequestParam(required = false) String departureAirportOrCityOrStateSearchTerm,
            @RequestParam(required = false) String destinationAirportOrCityOrStateSearchTerm,
            @RequestParam(required = false) Integer passengers,
            @RequestParam(required = false) Boolean lookForSimilarTimingFlights,
            HttpSession httpSession, HttpServletResponse response) throws IOException {

//  todo
//       User loggedUser = (User) session.getAttribute(UsersController.USER_KEY);
//        if (loggedUser == null || !loggedUser.isAdmin()) {
//            response.sendRedirect(baseURL);
//            return null;
//        }

        if (departureTimestamp!=null && departureTimestamp.trim().equals(""))
            departureTimestamp = null;

        if ( flight_id!=null && (flight_id.equals(0L) || flight_id.equals(0)) )
            flight_id = null;

        if (passengers!=null && passengers.equals(0))
            passengers = null;

        if (lookForSimilarTimingFlights!=null && !lookForSimilarTimingFlights.booleanValue() && departureTimestamp==null)
            lookForSimilarTimingFlights = null;

        if (departureAirportOrCityOrStateSearchTerm!=null && departureAirportOrCityOrStateSearchTerm.trim().equals(""))
            departureAirportOrCityOrStateSearchTerm = null;

        if (destinationAirportOrCityOrStateSearchTerm!=null && destinationAirportOrCityOrStateSearchTerm.trim().equals(""))
            destinationAirportOrCityOrStateSearchTerm = null;

        List<Flight> flights = flightService.find(flight_id, departureTimestamp, departureAirportOrCityOrStateSearchTerm, destinationAirportOrCityOrStateSearchTerm, passengers, lookForSimilarTimingFlights);

        ModelAndView responsePage = new ModelAndView("flights");
        responsePage.addObject("flights", flights);
        return responsePage;
    }
}
