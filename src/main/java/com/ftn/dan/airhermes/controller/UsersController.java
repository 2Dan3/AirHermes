package com.ftn.dan.airhermes.controller;

import com.ftn.dan.airhermes.model.entity.User;
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
import java.util.List;

@Controller
@RequestMapping(value = "/users")
public class UsersController {
    public static final String USER_KEY = "loggedUser";

    @Autowired
    private UserService userService;

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

//  todo
//        User loggedUser = (User) session.getAttribute(UsersController.USER_KEY);
//        if (loggedUser == null || !loggedUser.isAdmin()) {
//            response.sendRedirect(baseURL);
//            return null;
//        }

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
            HttpSession session, HttpServletResponse response) throws IOException {

        try {
            User existingUser = userService.find(username, email);
            if (existingUser != null) {
                throw new Exception("Username / email already exists!");
            }
            if (("").equals(username) || ("").equals(email) || ("").equals(password)) {
                throw new Exception("Username, email and password are required!");
            }
            if (!password.equals(repeatedPassword)) {
                throw new Exception("Repeated password does not match!");
            }
            if (("").equals(name) || ("").equals(surname)) {
                throw new Exception("Name and last name are required!");
            }
            if (dateOfBirth == null) {
                throw new Exception("Date of birth is required!");
            }

            User user = new User(null, name, surname, username, password, email, Timestamp.valueOf(dateOfBirth), Timestamp.valueOf(LocalDateTime.now()), false, false);
            userService.save(user);

            response.sendRedirect("login");
            return null;

        } catch (Exception ex) {
            String poruka = ex.getMessage();
//            if ("" == poruka) {
            if ("".equals(poruka)) {
                poruka = "Registration was unsuccessful!";
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
            HttpSession session, HttpServletResponse response) throws IOException {
        try {
            User user = userService.findByCredentials(username, password);
            if (user == null) {
                throw new Exception("Invalid credentials!");
            }

            session.setAttribute(UsersController.USER_KEY, user);

//            response.sendRedirect(baseURL);
            response.sendRedirect("../flights");
            return null;

        } catch (Exception ex) {
            String poruka = ex.getMessage();
//            if ("" == poruka) {
            if ("".equals(poruka)) {
                poruka = "Sign in was unsuccessful!";
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
        response.sendRedirect("../../flights");
    }
}
