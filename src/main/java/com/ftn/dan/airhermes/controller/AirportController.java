package com.ftn.dan.airhermes.controller;

import com.ftn.dan.airhermes.model.entity.Airport;
import com.ftn.dan.airhermes.model.entity.Location;
import com.ftn.dan.airhermes.service.AirportService;
import com.ftn.dan.airhermes.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

@Controller
@RequestMapping(value = "/airports")
public class AirportController {

    @Autowired
    private AirportService airportService;
    @Autowired
    private LocationService locationService;

    @Autowired
    private ServletContext servletContext;
    private String baseURL;

    @PostConstruct
    public void init() {
        baseURL = servletContext.getContextPath() + "/";
    }

    @GetMapping
    public ModelAndView getAll(
            HttpSession httpSession, HttpServletResponse response) throws IOException {

        List<Airport> airportsAndTheirLocations = airportService.findAll();
        List<Location> allLocations = locationService.findAll();

        ModelAndView mov = new ModelAndView("airportsPage");
        mov.addObject("airports", airportsAndTheirLocations);
        mov.addObject("allLocations", allLocations);
        return mov;
    }

    @PostMapping
    public void addNewAirport(
            @RequestParam(name = "codeName") String codeName,
            @RequestParam(name = "locationId") Long locationID,
            HttpSession httpSession, HttpServletResponse response) throws IOException {

        airportService.save(codeName, locationID);

        response.sendRedirect(baseURL + "airports");
    }


}
