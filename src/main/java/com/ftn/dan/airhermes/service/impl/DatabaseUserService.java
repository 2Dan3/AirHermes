package com.ftn.dan.airhermes.service.impl;

import com.ftn.dan.airhermes.DAO.UserDAO;
import com.ftn.dan.airhermes.model.entity.User;
import com.ftn.dan.airhermes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseUserService implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public List<User> find(String name, String surname, String username, String email, Boolean admin, Boolean blocked) {
        return userDAO.find(name, surname, username, email, admin, blocked);
    }

    @Override
    public User findByID(Long id) {
        return userDAO.findByID(id);
    }

    @Override
    public User find(String username, String email) {
        return userDAO.find(username, email);
    }

    @Override
    public void save(User user) {
        userDAO.save(user);
    }

    @Override
    public User findByCredentials(String username, String password) {
        return userDAO.findByCredentials(username, password);
    }
}
