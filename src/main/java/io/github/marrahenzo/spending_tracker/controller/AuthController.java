package io.github.marrahenzo.spending_tracker.controller;

import io.github.marrahenzo.spending_tracker.dto.ErrorDTO;
import io.github.marrahenzo.spending_tracker.dto.LoginRequest;
import io.github.marrahenzo.spending_tracker.dto.SignupRequest;
import io.github.marrahenzo.spending_tracker.dto.SuccessDTO;
import io.github.marrahenzo.spending_tracker.service.AuthService;
import io.github.marrahenzo.spending_tracker.service.UserService;
import io.github.marrahenzo.spending_tracker.util.Constants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessDTO> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        var user = this.authService.validateLogin(request);
        if (user.isEmpty()) throw new IllegalArgumentException("Invalid login credentials");

        var auth = new UsernamePasswordAuthenticationToken(user, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);

        var session = httpRequest.getSession(true);
        session.setAttribute(Constants.SESSION_USER_ID, user.get().getId());
        return ResponseEntity.ok().body(SuccessDTO.builder().message("Log in successful").build());
    }

    @PostMapping("/logout")
    public ResponseEntity<SuccessDTO> logout(HttpServletRequest request) {
        request.getSession(false).invalidate();
        return ResponseEntity.ok().body(SuccessDTO.builder().message("Log out successful").build());
    }

    @PostMapping("/signup")
    public ResponseEntity<SuccessDTO> signup(@RequestBody SignupRequest request) {
        authService.createUser(request);
        return ResponseEntity.ok().body(SuccessDTO.builder().message("Sign up successful").build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleException() {
        return ResponseEntity.badRequest()
                .body(
                        ErrorDTO.builder()
                                .message("Error custom del back")
                                .errorCode("403")
                                .exception("IllegalArgumentException")
                                .build()
                );
    }
}
