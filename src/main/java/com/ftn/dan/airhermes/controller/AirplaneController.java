package com.ftn.dan.airhermes.controller;

import com.ftn.dan.airhermes.model.entity.Airplane;
import com.ftn.dan.airhermes.service.AirplaneService;
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
@RequestMapping(value = "/airplanes")
public class AirplaneController {

    @Autowired
    private AirplaneService airplaneService;

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

        List<Airplane> airplanes = airplaneService.findAll();

        ModelAndView mov = new ModelAndView("airplanesPage");
        mov.addObject("airplanes", airplanes);
        return mov;
    }

    @PostMapping
    public void addNewAirplane(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "seatRows") Integer rows,
            @RequestParam(name = "seatColumns") Integer columns,
            HttpSession httpSession, HttpServletResponse response) throws IOException {

        airplaneService.save(new Airplane(null, name, rows, columns));

        response.sendRedirect(baseURL + "airplanes");
    }
}
