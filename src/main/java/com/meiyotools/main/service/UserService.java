package com.meiyotools.main.service;

import com.meiyotools.main.controller.LoginController;
import com.meiyotools.main.model.entity.User;
import com.meiyotools.main.model.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;
    private PasswordEncoder passwordEncoder;
    static final Key SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Autowired
    public UserService(UserRepository pRepository, PasswordEncoder pPasswordEncoder) {
        this.repository = pRepository;
        this.passwordEncoder = pPasswordEncoder;
        //this.createDefaultUser();
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
                new User("admin", "meiyo40@gmail.com", "apedemak40")
        );
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

    public boolean isLogged(HttpServletRequest request) {
        String username = (String)request.getSession().getAttribute("user");
        Cookie[] cookies = request.getCookies();
        String token = null;
        boolean userExist = false;
        boolean tokenExist = false;

        if(username != null)
            userExist = true;

        if(cookies != null) {
            for (Cookie cookie: cookies) {
                if(cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
            if(token != null) {
                try {
                    String subject = Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token).getBody().getSubject();
                    request.getSession().setAttribute("user", subject);
                    tokenExist = true;
                }catch (JwtException e) {
                    tokenExist = false;
                }
            }
        }
        return userExist && tokenExist;
    }

    public User getUser(String username) {
        Optional<User> user = repository.findByUsername(username);
        if(user.isPresent()) {
            return user.get();
        } else {
            throw new RequestRejectedException("User not found.");
        }
    }

    public Cookie createUserCookieToken(User logs) {
        User user = this.logUser(logs.getUsername(), logs.getPassword());
        if(user != null) {
            String jws = Jwts.builder().setSubject(user.getUsername()).signWith(SECRET).compact();
            Cookie token = new Cookie("token", jws);
            token.setMaxAge(5 * 60);
            token.setPath("/");
            return token;
        } else {
            return null;
        }
    }
}
