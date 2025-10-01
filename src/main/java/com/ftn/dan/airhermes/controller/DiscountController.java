package com.ftn.dan.airhermes.controller;

import com.ftn.dan.airhermes.model.entity.DiscountStandard;
import com.ftn.dan.airhermes.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/discounts")
@CrossOrigin(origins = "http://localhost:8080")
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

    @GetMapping(value = "/async")
    @ResponseBody
    public Map<String, Object> getAllAsync(
            HttpSession httpSession, HttpServletResponse response) throws IOException {

        List<DiscountStandard> discounts = discountService.findAll();

        Map<String, Object> retval = new LinkedHashMap<>();

        retval.put("status", "ok");
        retval.put("discounts", discounts);

        return retval;
    }

    @PostMapping
    @ResponseBody
    public Map<String, Object> defineDiscount(
            @RequestParam(name = "coefficient") Double coefficient,
            @RequestParam(name = "validUntilDate")
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime localDateTime,
            HttpSession httpSession, HttpServletResponse response) throws IOException {

        Long newDiscountID = discountService.save(new DiscountStandard(null, coefficient, Timestamp.valueOf(localDateTime)));

        Map<String, Object> retval = new LinkedHashMap<>();

        if (newDiscountID != null && newDiscountID != 0L) {
            DiscountStandard dis = discountService.findByID(newDiscountID);
            retval.put("status", "created");
            retval.put("discount", dis);
        }
        else
            retval.put("status", "bad request");

        return retval;
//        response.sendRedirect(baseURL + "discounts");
    }
}