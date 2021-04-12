package com.meiyotools.main.service;

import com.meiyotools.main.model.entity.User;
import com.meiyotools.main.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository pRepository) {
        this.repository = pRepository;
    }

    public User registerNewUser(User user) {
        Optional<User> userExist = repository.findByUsername(user.getUsername());
        if(userExist.isPresent()) {
            throw new RequestRejectedException("User already exist");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return repository.save(user);
        }
    }
}
