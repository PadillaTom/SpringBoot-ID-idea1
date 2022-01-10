package com.idforideas.ideauno.auth.controller;

import com.idforideas.ideauno.auth.service.UserAuthService;
import com.idforideas.ideauno.auth.service.UserDetailsCustomService;
import com.idforideas.ideauno.model.request.AuthenticationRequest;
import com.idforideas.ideauno.model.request.RegisterRequest;
import com.idforideas.ideauno.model.response.AuthenticationResponse;
import com.idforideas.ideauno.model.response.RegisterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    @Autowired
    private UserDetailsCustomService userDetailsCustomService;

    @Autowired
    private UserAuthService userAuthServ;

    // Signup
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse signUp(@RequestBody RegisterRequest userToCreate) {
        return userDetailsCustomService.signupUser(userToCreate);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return userAuthServ.loginAttempt(authenticationRequest);
    }

}