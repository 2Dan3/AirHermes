package com.ftn.dan.airhermes.DAO;

import com.ftn.dan.airhermes.model.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface UserDAO {
    User find(String username, String email);

    List<User> find(String name, String surname, String username, String email, Boolean admin, Boolean blocked, String sortAndDirection);

    User findByID(Long id);

    void save(User user);

    User findByCredentials(String username, String password);

    User find(String username);

    User findByEmail(String email);

    boolean updateBasicData(User user, String name, String surname, String username, String email, LocalDateTime dateOfBirth);

    boolean updatePassword(User user, String newPassword);

    void updateBlockedStatus(User user, Boolean blocked);
}
