package com.ftn.dan.airhermes.controller;

import com.ftn.dan.airhermes.model.entity.DiscountStandard;
import com.ftn.dan.airhermes.service.DiscountService;
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
import java.util.List;

@Controller
@RequestMapping(value = "/discounts")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private ServletContext servletContext;
    private String baseURL;

    @PostConstruct
    public void init() {
        baseURL = servletContext.getContextPath() + "/";
    }

    @GetMapping
    public ModelAndView getAllNonExpired(
            HttpSession httpSession, HttpServletResponse response) throws IOException {

        List<DiscountStandard> discounts = discountService.findAll();

        ModelAndView mov = new ModelAndView("discountsPage");
        mov.addObject("discounts", discounts);
        return mov;
    }

    @PostMapping
    public void defineDiscount(
            @RequestParam(name = "coefficient") Double coefficient,
            @RequestParam(name = "validUntilDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime localDateTime,
            HttpSession httpSession, HttpServletResponse response) throws IOException {

        discountService.save(new DiscountStandard(null, coefficient, Timestamp.valueOf(localDateTime)));

        response.sendRedirect(baseURL + "discounts");
    }
}