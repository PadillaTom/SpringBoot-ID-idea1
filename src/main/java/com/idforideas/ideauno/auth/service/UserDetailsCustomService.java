package com.idforideas.ideauno.auth.service;

import com.idforideas.ideauno.model.entity.UserEntity;
import com.idforideas.ideauno.model.mapper.AuthenticationMapper;
import com.idforideas.ideauno.model.request.RegisterRequest;
import com.idforideas.ideauno.model.response.RegisterResponse;
import com.idforideas.ideauno.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsCustomService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthenticationMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserDetailsCustomService(
            @Lazy AuthenticationMapper userMapper,
            @Lazy UserRepository userRepository,
            @Lazy PasswordEncoder passwordEncoder
    ) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponse signupUser(RegisterRequest userToCreate) {
        var matchingUser = userRepository.findByEmail(userToCreate.getEmail());
        if (matchingUser.isPresent()) throw new IllegalArgumentException("El usuario ya existe");

        userToCreate.setPassword(passwordEncoder.encode(userToCreate.getPassword()));
        UserEntity newUserEntity = userMapper.toEntity(userToCreate);
        newUserEntity = userRepository.save(newUserEntity);

        UserDetails newUserDetail = new User(
                newUserEntity.getEmail(),
                newUserEntity.getPassword(),
                List.of()
        );
        return userMapper.toDTO(newUserEntity);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var foundUser = userRepository.findByEmail(username)
                .orElseThrow();

        // Authorities List
        Collection<GrantedAuthority> authorities = foundUser.getRoles().stream()
                .map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getName()))
                .collect(Collectors.toList());

        // === Set Spring Security USER en el Context ===
        return new User(
                foundUser.getEmail(),
                foundUser.getPassword(),
                authorities
        );
    }

}