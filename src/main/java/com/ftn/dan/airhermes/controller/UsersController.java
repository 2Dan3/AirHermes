package com.ftn.dan.airhermes.controller;

import com.ftn.dan.airhermes.model.dto.FlightDTO;
import com.ftn.dan.airhermes.model.dto.ReservationDTO;
import com.ftn.dan.airhermes.model.entity.*;
import com.ftn.dan.airhermes.service.FlightService;
import com.ftn.dan.airhermes.service.LoyaltyCardService;
import com.ftn.dan.airhermes.service.ReservationService;
import com.ftn.dan.airhermes.service.UserService;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping(value = "/users")
public class UsersController {
    public static final String USER_KEY = "loggedUser";

    @Autowired
    private UserService userService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private FlightService flightService;
    @Autowired
    private LoyaltyCardService loyaltyCardService;

    @Autowired
    ServletContext servletContext;
    private String baseURL;

    @PostConstruct
    public void init() { baseURL = servletContext.getContextPath() + "/";}

    @GetMapping
    public ModelAndView index(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Boolean admin,
            @RequestParam(required = false) Boolean blocked,
            HttpSession session, HttpServletResponse response) throws IOException {

        User loggedUser = (User) session.getAttribute(UsersController.USER_KEY);
        if (loggedUser == null || !loggedUser.isAdmin()) {
            response.sendRedirect(baseURL + "flights");
            return null;
        }

        if (name!=null && name.trim().equals(""))
            name = null;

        if (surname!=null && surname.trim().equals(""))
            surname = null;

        if (username!=null && username.trim().equals(""))
            username = null;

        if (email!=null && email.trim().equals(""))
            email = null;

        List<User> users = userService.find(name, surname, username, email, admin, blocked);

        ModelAndView mov = new ModelAndView("users");
        mov.addObject("users", users);
        return mov;
    }

    @GetMapping(value="/register")
    public ModelAndView register() {
        ModelAndView retval = new ModelAndView("register");
        return retval;
    }

    @PostMapping(value="/register")
    public ModelAndView postRegister(
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String repeatedPassword,
            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")LocalDateTime dateOfBirth,
            HttpSession session, HttpServletResponse response, Locale locale) throws IOException {

//        Locale locale = LocaleContextHolder.getLocale();
        try {
            User existingUser = userService.find(username, email);
            if (existingUser != null) {
                throw new Exception(ResourceBundle.getBundle("messages.messages", locale).getString("errors.usernameEmailExist"));
            }
            if (("").equals(username) || ("").equals(email) || ("").equals(password)) {
                throw new Exception(ResourceBundle.getBundle("messages.messages", locale).getString("errors.credentialsRequired"));
            }
            if (!password.equals(repeatedPassword)) {
                throw new Exception(ResourceBundle.getBundle("messages.messages", locale).getString("errors.repeatPasswordMatch"));
            }
            if (("").equals(name) || ("").equals(surname)) {
                throw new Exception(ResourceBundle.getBundle("messages.messages", locale).getString("errors.namesRequired"));
            }
            if (dateOfBirth == null) {
                throw new Exception(ResourceBundle.getBundle("messages.messages", locale).getString("errors.birthRequired"));
            }

            User user = new User(null, name, surname, username, password, email, Timestamp.valueOf(dateOfBirth), Timestamp.valueOf(LocalDateTime.now()), false, false);
            userService.save(user);

            response.sendRedirect("login");
            return null;

        } catch (Exception ex) {
            String poruka = ex.getMessage();
//            if ("" == poruka) {
            if ("".equals(poruka)) {
                poruka = ResourceBundle.getBundle("messages.messages", locale).getString("errors.registrationSuccess");
            }

            ModelAndView retval = new ModelAndView("register");
            retval.addObject("poruka", poruka);

            return retval;
        }
    }

    @GetMapping(value="/login")
    public ModelAndView login() {
        ModelAndView retval = new ModelAndView("login");
        return retval;
    }

    @PostMapping(value="/login")
    public ModelAndView postLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session, HttpServletResponse response, Locale locale) throws IOException {
        try {
            User user = userService.findByCredentials(username, password);
            if (user == null) {
                throw new Exception(ResourceBundle.getBundle("messages.messages", locale).getString("errors.credentialsValid"));
            }
            if (user.isBlocked()) {
                throw new Exception(ResourceBundle.getBundle("messages.messages", locale).getString("errors.profileBlocked"));
            }

            session.setAttribute(UsersController.USER_KEY, user);

//            response.sendRedirect(baseURL);
            response.sendRedirect(baseURL + "flights");
            return null;

        } catch (Exception ex) {
            String poruka = ex.getMessage();
//            if ("" == poruka) {
            if ("".equals(poruka)) {
                poruka = ResourceBundle.getBundle("messages.messages", locale).getString("errors.loginSuccess");
            }

            ModelAndView retval = new ModelAndView("login");
            retval.addObject("poruka", poruka);

            return retval;
        }
    }

    @GetMapping(value="/logout")
    public void logout(HttpSession session, HttpServletResponse response) throws IOException {
        session.invalidate();

//        response.sendRedirect(baseURL);
        response.sendRedirect(baseURL + "flights");
    }

    @GetMapping(value="/profile")
    public ModelAndView getProfile(@RequestParam String username,
                                HttpSession session, HttpServletResponse response) throws IOException {
        User loggedUser = (User) session.getAttribute(UsersController.USER_KEY);
        if (loggedUser == null || (!loggedUser.isAdmin() && !loggedUser.getUsername().equals(username))) {
            response.sendRedirect(baseURL + "users");
            return null;
        }

        User user = userService.find(username);
        if (user == null) {
            response.sendRedirect(baseURL + "users");
            return null;
        }

        List<ReservationDTO> reservationsAndFlights = reservationService.findAllWithFlightsByUserId(user.getId());
        List<FlightDTO> wishlist = flightService.findFlightsFromWishlist(loggedUser);
        LoyaltyCard loyaltyCard = loyaltyCardService.findBy(user);
        String cardStatus = loyaltyCardService.findCreationRequest(user);

        ModelAndView retval = new ModelAndView("profile");
        retval.addObject("user", user);
        retval.addObject("reservationDTOs", reservationsAndFlights);
        retval.addObject("wishlist", wishlist);
        retval.addObject("loyaltyCard", loyaltyCard);
        retval.addObject("cardStatus", cardStatus);

        return retval;
    }

    @PostMapping(value="/edit")
    public void edit(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String surname,
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "") String email,
            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dateOfBirth,
                     HttpSession session, HttpServletResponse response, Locale locale) throws IOException {
        User loggedUser = (User) session.getAttribute(UsersController.USER_KEY);
        if (loggedUser == null) {
            response.sendRedirect(baseURL + "users/profile?username=" + loggedUser.getUsername());
            return;
        }

        try {
            User existingUser;
            if ((existingUser = userService.find(username)) != null && !Objects.equals(existingUser.getUsername(), loggedUser.getUsername())) {
                throw new Exception(ResourceBundle.getBundle("messages.messages", locale).getString("errors.usernameAvailable"));
            }
            if ((existingUser = userService.findByEmail(email)) != null && !Objects.equals(existingUser.getEmail(), loggedUser.getEmail())) {
                throw new Exception(ResourceBundle.getBundle("messages.messages", locale).getString("errors.emailAvailable"));
            }
            if (("").equals(name.trim()) || ("").equals(surname.trim())) {
                throw new Exception(ResourceBundle.getBundle("messages.messages", locale).getString("errors.namesRequired"));
            }
            if (dateOfBirth == null) {
                throw new Exception(ResourceBundle.getBundle("messages.messages", locale).getString("errors.birthRequired"));
            }

            boolean successful = userService.updateBasicData(loggedUser, name, surname, username, email, dateOfBirth);
            response.sendRedirect(baseURL + "users/profile?username=" + loggedUser.getUsername());

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
//            String poruka = ex.getMessage();
////            if ("" == poruka) {
//            if ("".equals(poruka)) {
//                poruka = "Registration was unsuccessful!";
//            }
            response.sendRedirect(baseURL + "users/profile?username=" + loggedUser.getUsername());
            return;

//            ModelAndView retval = new ModelAndView("profile");
//            retval.addObject("poruka", poruka);

        }
    }

    @PostMapping(value = "/changePassword")
    public void edit(@RequestParam(name = "password") String newPassword,
                     @RequestParam(name = "repeatedPassword") String repeatedNewPassword,
                HttpSession session, HttpServletResponse response, Locale locale) throws IOException {

        User loggedUser = (User) session.getAttribute(UsersController.USER_KEY);
        if (loggedUser == null) {
            response.sendRedirect(baseURL + "users");
            return;
        }

        try {
            if (("").equals(newPassword) || !newPassword.equals(repeatedNewPassword)) {
                throw new Exception(ResourceBundle.getBundle("messages.messages", locale).getString("errors.passwordsMatchingAndProvided"));
            }

            boolean successful = userService.updatePassword(loggedUser, newPassword);
            response.sendRedirect(baseURL + "users/profile?username=" + loggedUser.getUsername());

        } catch (Exception ex) {
//            String poruka = ex.getMessage();
////            if ("" == poruka) {
//            if ("".equals(poruka)) {
//                poruka = "Registration was unsuccessful!";
//            }
            response.sendRedirect(baseURL + "users/profile?username=" + loggedUser.getUsername());
            return;
//            ModelAndView retval = new ModelAndView("profile");
//            retval.addObject("poruka", poruka);

        }
    }

    @PostMapping(value = "/block")
    public void changeUserBlockedStatus(
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "blocked", required = false) Boolean blocked,
            HttpSession session, HttpServletResponse response) throws IOException {

        User loggedUser = (User) session.getAttribute(UsersController.USER_KEY);
        if (loggedUser == null || !loggedUser.isAdmin()) {
            response.sendRedirect(baseURL + "flights");
            return;
        }

        User user = userService.findByID(userId);
        if (user == null || user.isAdmin()) {
            response.sendRedirect(baseURL + "flights");
            return;
        }

        if (blocked == null)
            blocked = false;

        userService.updateBlockedStatus(user, blocked);

        response.sendRedirect(baseURL + "users");
    }

}
