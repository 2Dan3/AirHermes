package com.ftn.dan.airhermes.controller;

import com.ftn.dan.airhermes.model.entity.*;
import com.ftn.dan.airhermes.service.*;
import org.apache.catalina.Store;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/reservations")
public class ReservationController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private LoyaltyCardService loyaltyCardService;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private ServletContext servletContext;
    private String baseURL;

    @PostConstruct
    public void init() { baseURL = servletContext.getContextPath() + "/";}

//    @GetMapping
//    public ModelAndView index(
//            @RequestParam(required = false) Long flight_id,
//            @RequestParam(required = false, name = "departureTimestamp")
//            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime rawParamDepartureDateTime,
//            @RequestParam(required = false) String departureAirportOrCityOrStateSearchTerm,
//            @RequestParam(required = false) String destinationAirportOrCityOrStateSearchTerm,
//            @RequestParam(required = false) Integer passengers,
//            @RequestParam(required = false) Boolean lookForSimilarTimingFlights,
//            HttpSession httpSession, HttpServletResponse response) throws IOException {
//
////  todo
////       User loggedUser = (User) session.getAttribute(UsersController.USER_KEY);
////        if (loggedUser == null || !loggedUser.isAdmin()) {
////            response.sendRedirect(baseURL);
////            return null;
////        }
//
////        if (departureTimestamp!=null && departureTimestamp.trim().equals(""))
////            departureTimestamp = null;
//
//        Timestamp departureTimestamp = null;
//        if (rawParamDepartureDateTime != null)
//            departureTimestamp = Timestamp.valueOf(rawParamDepartureDateTime);
//
//        if ( flight_id!=null && (flight_id.equals(0L) || flight_id.equals(0)) )
//            flight_id = null;
//
//        if (passengers!=null && passengers.equals(0))
//            passengers = null;
//
//        if ( lookForSimilarTimingFlights == null || Boolean.FALSE.equals(lookForSimilarTimingFlights) || departureTimestamp==null )
//            lookForSimilarTimingFlights = false;
//
//        if (departureAirportOrCityOrStateSearchTerm!=null && departureAirportOrCityOrStateSearchTerm.trim().equals(""))
//            departureAirportOrCityOrStateSearchTerm = null;
//
//        if (destinationAirportOrCityOrStateSearchTerm!=null && destinationAirportOrCityOrStateSearchTerm.trim().equals(""))
//            destinationAirportOrCityOrStateSearchTerm = null;
//
//        List<Flight> flights = flightService.find(flight_id, departureTimestamp, departureAirportOrCityOrStateSearchTerm, destinationAirportOrCityOrStateSearchTerm, passengers, lookForSimilarTimingFlights);
//
//        ModelAndView responsePage = new ModelAndView("flights");
//        responsePage.addObject("flights", flights);
//        return responsePage;
//    }

    @GetMapping(value="/create")
    public ModelAndView getCreationPage(
            @RequestParam(name = "flightId") Long[] flightIds,
            HttpSession session, HttpServletResponse response) throws IOException {

//        User loggedUser = (User) session.getAttribute(UsersController.USER_KEY);
//        if (loggedUser == null || !loggedUser.isAdmin()) {
//            response.sendRedirect(baseURL + "flights");
//            return null;
//        }

        List<Flight> flights = flightService.findAllBy(flightIds);

        ModelAndView retval = new ModelAndView("reservationCreation");
        retval.addObject("flights", flights);

        return retval;
    }

    @PostMapping(value="/make")
    public void create(
            @RequestParam(name="flightId") Long[] flightIDs,
            @RequestParam(name = "seatNumber") Integer[] seatNumbers,
            @RequestParam(name = "passengerName") String[] passengerNames,
            @RequestParam(name = "passengerSurname") String[] passengerSurnames,
            @RequestParam(name = "passportNumber") Integer[] passportNumbers,
            @RequestParam(name = "loyaltyPointsToUse", required = false) Integer loyaltyPointsToUse,
                       HttpSession session, HttpServletResponse response) throws IOException {

        User loggedUser = (User) session.getAttribute(UsersController.USER_KEY);
//        if (loggedUser == null || !loggedUser.isAdmin()) {
//            response.sendRedirect(baseURL + "flights");
//            return;
//        }

        if (loyaltyPointsToUse == null || loyaltyPointsToUse < 0) {
            loyaltyPointsToUse = 0;
        }

        LoyaltyCard loyaltyCard = loyaltyCardService.findBy(loggedUser);

        if (loyaltyCard == null) {
            loyaltyPointsToUse = 0;
        } else {
            if (loyaltyCard.getPointsCollected() < loyaltyPointsToUse) {
//             return msg not enough points on loyalty card
//                return;
                loyaltyPointsToUse = loyaltyCard.getPointsCollected();
            }
        }

        List<Flight> flights = flightService.findByID(flightIDs);
//         handle the error if no flight found
        if (flights.isEmpty())
            return;

//        reservation = reservationService.save(reservation, flightIDs);

        List<FlightTicket> newTickets = new ArrayList<>();

        double sumPriceOfReservationTicketsIncludingFlightAndPersonalDiscounts = 0.0;

        for (int i = 0; i < flights.size(); i++) {

            DiscountStandard discountStandard = flights.get(i).getDiscountStandard();
            double discountCoefficient = discountStandard == null || discountService.isExpired(discountStandard.getId()) ? 0 : discountStandard.getDiscountCoefficient();
            double priceAfterStandardDiscount = flights.get(i).getFlightTicketPrice() * (1 - discountCoefficient);
            double priceAfterStandardAndPersonalDiscounts = (1 - (0.07 * loyaltyPointsToUse)) * priceAfterStandardDiscount;

            for (int j = 0; j < passportNumbers.length; j++) {

                FlightTicket ticket = new FlightTicket(flights.get(i), seatNumbers[j], passengerNames[j], passengerSurnames[j], passportNumbers[j], null, priceAfterStandardAndPersonalDiscounts);
                newTickets.add(ticket);

                sumPriceOfReservationTicketsIncludingFlightAndPersonalDiscounts += priceAfterStandardAndPersonalDiscounts;
            }

        }

        FlightReservation reservation = new FlightReservation(loggedUser, newTickets, sumPriceOfReservationTicketsIncludingFlightAndPersonalDiscounts);
        reservationService.saveReservationAndTickets(reservation, flightIDs);

        if (loyaltyCard != null) {
            double newBalanceSpent = loyaltyCard.getSpentMoneyUnconvertedToPoints() + sumPriceOfReservationTicketsIncludingFlightAndPersonalDiscounts;
            loyaltyCard.setPointsCollected((int) (newBalanceSpent / 30000));
            loyaltyCard.setSpentMoneyUnconvertedToPoints(newBalanceSpent % 30000);
            loyaltyCardService.update(loyaltyCard);
        }

        response.sendRedirect(baseURL + "flights");
    }
}
