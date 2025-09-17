package com.ftn.dan.airhermes.service;

import com.ftn.dan.airhermes.model.entity.User;

import java.util.List;

public interface UserService {
    List<User> find(String name, String surname, String username, String email, Boolean admin, Boolean blocked);

    User findByID(Long id);

    User find(String username, String email);

    void save(User user);

    User findByCredentials(String username, String password);
}
