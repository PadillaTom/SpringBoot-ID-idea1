package com.idforideas.ideauno.auth.service;

import com.idforideas.ideauno.model.mapper.AuthenticationMapper;
import com.idforideas.ideauno.model.request.AuthenticationRequest;
import com.idforideas.ideauno.model.response.AuthenticationResponse;
import com.idforideas.ideauno.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserAuthService {

    private final AuthenticationManager authenticationManager;

    private final AuthenticationMapper authenticationMapper;

    private final UserDetailsCustomService userDetailsCustomService;

    private final UserRepository userRepository;

    public AuthenticationResponse loginAttempt(AuthenticationRequest authenticationRequest) {
        UserDetails userDetails = userDetailsCustomService.loadUserByUsername(authenticationRequest.getEmail());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword(),
                userDetails.getAuthorities()
        );
        authenticationManager.authenticate(authenticationToken);
        return authenticationMapper.userDetailsAndJwt2LoginResponseDTO(userDetails);
    }

}