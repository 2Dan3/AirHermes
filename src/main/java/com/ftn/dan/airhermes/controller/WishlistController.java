package com.ftn.dan.airhermes.controller;

import com.ftn.dan.airhermes.model.entity.Flight;
import com.ftn.dan.airhermes.model.entity.User;
import com.ftn.dan.airhermes.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping(value = "/wishlist")
public class WishlistController {

    @Autowired
    ServletContext servletContext;
    private String baseURL;

    @Autowired
    private WishlistService wishlistService;

    @PostConstruct
    public void init() { baseURL = servletContext.getContextPath() + "/";}


    @PostMapping
    public void addFlightsToWishlist(
            @RequestParam(name = "flightId") Long[] flightIDs,
            HttpSession httpSession, HttpServletResponse response) throws IOException {

        User loggedUser = (User) httpSession.getAttribute(UsersController.USER_KEY);
        if (loggedUser == null) {
            response.sendRedirect(baseURL + "users/login");
            return;
        }

        wishlistService.addFlights(flightIDs, loggedUser);

        response.sendRedirect(baseURL + "flights");
        return;
    }

    @PostMapping(value = "/remove")
    public void removeFlightFromWishlist(
            @RequestParam(name = "flightId") Long flightId,
            HttpSession httpSession, HttpServletResponse response) throws IOException {

        User loggedUser = (User) httpSession.getAttribute(UsersController.USER_KEY);
        if (loggedUser == null) {
            response.sendRedirect(baseURL + "users/login");
            return;
        }

        wishlistService.removeFlight(flightId, loggedUser);

        response.sendRedirect(baseURL + "users/profile?username=" + loggedUser.getUsername());
        return;
    }
}
