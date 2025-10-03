package com.ftn.dan.airhermes.controller;

import com.ftn.dan.airhermes.model.entity.LoyaltyCardCreationRequest;
import com.ftn.dan.airhermes.model.entity.User;
import com.ftn.dan.airhermes.model.enums.LoyaltyCardCreationRequestStatus;
import com.ftn.dan.airhermes.service.LoyaltyCardService;
import com.ftn.dan.airhermes.service.UserService;
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
@RequestMapping(value = "/cards")
public class LoyaltyCardController {

    @Autowired
    ServletContext servletContext;
    private String baseURL;

    @Autowired
    private LoyaltyCardService loyaltyCardService;
    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() { baseURL = servletContext.getContextPath() + "/";}

    @GetMapping
    public ModelAndView getAllCardCreationRequests(HttpSession session, HttpServletResponse response) throws IOException {

        User loggedUser = (User) session.getAttribute(UsersController.USER_KEY);
        if (loggedUser == null || !loggedUser.isAdmin()) {
            response.sendRedirect(baseURL + "flights");
            return null;
        }

        List<LoyaltyCardCreationRequest> requests = loyaltyCardService.findAll();

        ModelAndView mov = new ModelAndView("requests");
        mov.addObject("requests", requests);
        return mov;
    }

    @PostMapping
    public void sendLoyaltyCardCreationRequest(HttpSession httpSession, HttpServletResponse response) throws IOException {

        User loggedUser = (User) httpSession.getAttribute(UsersController.USER_KEY);
        if (loggedUser == null) {
            response.sendRedirect(baseURL + "users/login");
            return;
        }

        loyaltyCardService.sendOrResendRequest(loggedUser);

        response.sendRedirect(baseURL + "users/profile?username=" + loggedUser.getUsername());
    }

    @PostMapping(value = "/resolve")
    public void changeLoyaltyCardCreationRequestStatus (
            @RequestParam(name = "userId") Long ownerID,
            @RequestParam(name = "newStatus") String status,
            HttpSession session, HttpServletResponse response) throws IOException {

        User loggedUser = (User) session.getAttribute(UsersController.USER_KEY);
        if (loggedUser == null || !loggedUser.isAdmin()) {
            response.sendRedirect(baseURL + "flights");
            return;
        }

        User foundUser = userService.findByID(ownerID);
        if (foundUser == null) {
            response.sendRedirect(baseURL + "cards");
            return;
        }

        String foundReqStatus = loyaltyCardService.findCreationRequest(foundUser);
        if (foundReqStatus == null) {
            response.sendRedirect(baseURL + "cards");
            return;
        }

        LoyaltyCardCreationRequestStatus status1;
        try {
            status1 = LoyaltyCardCreationRequestStatus.valueOf(status);
        }catch (IllegalArgumentException e) {
            response.sendRedirect(baseURL + "cards");
            return;
        }

        loyaltyCardService.updateStatus(new LoyaltyCardCreationRequest(foundUser, status1));
        response.sendRedirect(baseURL + "cards");
    }
}