package com.ftn.dan.airhermes.controller;

import com.ftn.dan.airhermes.model.entity.User;
import com.ftn.dan.airhermes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
}
