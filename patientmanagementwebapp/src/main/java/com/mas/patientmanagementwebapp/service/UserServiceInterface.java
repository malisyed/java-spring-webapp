package com.mas.patientmanagementwebapp.service;

import com.mas.patientmanagementwebapp.model.User;

public interface UserServiceInterface {
    boolean userExists(User user);
    void saveUser(User user);
}
