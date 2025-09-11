package com.ftn.dan.airhermes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/flights")
public class FlightsController {

    @GetMapping
    public ModelAndView index(HttpSession httpSession) {

        String msg = "Custom message template injection works!";

        ModelAndView responsePage = new ModelAndView("flights");
        responsePage.addObject("msg", msg);
        return responsePage;
    }
}
