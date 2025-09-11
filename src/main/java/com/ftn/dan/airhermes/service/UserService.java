package com.ftn.dan.airhermes.service;

import com.ftn.dan.airhermes.model.entity.User;

import java.util.List;

public interface UserService {
    List<User> find(String name, String surname, String username, String email, Boolean admin, Boolean blocked);
}
