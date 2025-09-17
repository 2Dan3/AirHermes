package com.ftn.dan.airhermes.DAO;

import com.ftn.dan.airhermes.model.entity.User;

import java.util.List;

public interface UserDAO {
    User find(String username, String email);

    List<User> find(String name, String surname, String username, String email, Boolean admin, Boolean blocked);

    User findByID(Long id);

    void save(User user);

    User findByCredentials(String username, String password);
}
