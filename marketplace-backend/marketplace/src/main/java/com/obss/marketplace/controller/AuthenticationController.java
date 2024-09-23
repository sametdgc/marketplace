package com.obss.marketplace.controller;

import com.obss.marketplace.dto.LoginResponse;
import com.obss.marketplace.dto.LoginUserDto;
import com.obss.marketplace.dto.RegisterUserDto;
import com.obss.marketplace.model.User;
import com.obss.marketplace.security.UserDetailsServiceImpl;
import com.obss.marketplace.service.AuthenticationService;
import com.obss.marketplace.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final UserDetailsServiceImpl userDetailsService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, UserDetailsServiceImpl userDetailsService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        // Load UserDetails from the authenticated user
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticatedUser.getEmail());

        // Generate JWT token using UserDetails
        String jwtToken = jwtService.generateToken(userDetails);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setRole(authenticatedUser.getRole().getName());

        return ResponseEntity.ok(loginResponse);
    }
}
