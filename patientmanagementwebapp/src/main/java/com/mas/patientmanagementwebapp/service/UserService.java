package com.mas.patientmanagementwebapp.service;

import com.mas.patientmanagementwebapp.dao.RoleRepository;
import com.mas.patientmanagementwebapp.dao.UserRepository;
import com.mas.patientmanagementwebapp.model.Role;
import com.mas.patientmanagementwebapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserService implements UserServiceInterface{
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean userExists(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null){
            return true;
        }
        return false;
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setStatus("1");
        Role userRole = roleRepository.findByRole("User");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }
}
