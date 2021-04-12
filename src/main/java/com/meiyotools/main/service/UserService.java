package com.meiyotools.main.service;

import com.meiyotools.main.model.entity.User;
import com.meiyotools.main.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository pRepository, PasswordEncoder pPasswordEncoder) {
        this.repository = pRepository;
        this.passwordEncoder = pPasswordEncoder;
        this.createDefaultUser();
    }

    public User registerNewUser(User user) {
        Optional<User> userExist = repository.findByUsername(user.getUsername());
        if(userExist.isPresent()) {
            throw new RequestRejectedException("User already exist.");
        } else {
            user.setPassword(
                    passwordEncoder.encode(
                            user.getPassword()));
            return repository.save(user);
        }
    }

    private void createDefaultUser() {
        this.registerNewUser(
                new User("admin", "apedemak40", null)
        );
        System.out.println("SELF: Default User created");
    }

    public User logUser(String username, String password) {
        Optional<User> user = repository.findByUsername(username);
        if(user.isPresent()) {
            if(passwordEncoder.matches(password, user.get().getPassword())) {
                user.get().setLastConnection(LocalDate.now());
                repository.save(user.get());
                return user.get();
            } else {
                throw new RequestRejectedException("Bad credentials.");
            }
        } else {
            throw new RequestRejectedException("Bad credentials.");
        }
    }
}
