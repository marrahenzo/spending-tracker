package io.github.marrahenzo.spending_tracker.service;

import io.github.marrahenzo.spending_tracker.dto.LoginRequest;
import io.github.marrahenzo.spending_tracker.dto.SignupRequest;
import io.github.marrahenzo.spending_tracker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Checks whether the provided user and password combination is valid
     *
     * @param request
     * @return
     */
    public Optional<User> validateLogin(LoginRequest request) {
        var user = this.userService.findByUsername(request.getUsername());
        if (user.isEmpty()) return Optional.empty();
        return this.doesPasswordMatch(user.get(), request.getPassword()) ? user : Optional.empty();
    }

    /**
     * Gets the user based off the provided username and
     * checks whether the provided plaintext password matches the user's encrypted password
     *
     * @param username
     * @param password
     * @return
     * @throws IllegalArgumentException
     */
    public boolean doesPasswordMatch(String username, String password) throws IllegalArgumentException {
        var user = userService.findByUsername(username);
        if (user.isEmpty()) throw new IllegalArgumentException("The provided user doesn't exist");
        return passwordEncoder.matches(password, user.get().getPassword());
    }

    /**
     * Checks whether the provided plaintext password matches the provided user's encrypted password
     *
     * @param user
     * @param password
     * @return
     * @throws IllegalArgumentException
     */
    public boolean doesPasswordMatch(User user, String password) throws IllegalArgumentException {
        return passwordEncoder.matches(password, user.getPassword());
    }

    /**
     * Creates a user in the database with the provided data
     *
     * @param request
     * @return
     */
    public User createUser(SignupRequest request) {
        var user = User.builder()
                .id(0)
                .username(request.getUsername())
                .name(request.getName())
                .password(this.passwordEncoder.encode(request.getPassword()))
                .build();

        return this.userService.save(user);
    }
}
