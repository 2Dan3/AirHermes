package com.ftn.dan.airhermes.model.entity;

import java.util.Date;

public class User {
    private String name, surname, username, password, email;
    private Date dateOfBirth, registrationTimestamp;
    private boolean admin = false;
    private boolean blocked = false;

}
