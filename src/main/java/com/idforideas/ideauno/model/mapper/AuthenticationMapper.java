package com.idforideas.ideauno.model.mapper;

import com.idforideas.ideauno.model.entity.Role;
import com.idforideas.ideauno.model.entity.UserEntity;
import com.idforideas.ideauno.model.request.RegisterRequest;
import com.idforideas.ideauno.model.response.AuthenticationResponse;
import com.idforideas.ideauno.model.response.RegisterResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthenticationMapper {

    public UserEntity toEntity(RegisterRequest dto) {
        UserEntity ent = new UserEntity();
        ent.setFirstName(dto.getFirstName());
        ent.setLastName(dto.getLastName());
        ent.setPassword(dto.getPassword());
        ent.setEmail(dto.getEmail());
        ent.setAge(dto.getAge());
        ent.setGender(dto.getGender());
        ent.setNationality(dto.getNationality());
        ent.setRoles(List.of(Role.builder().id(dto.getRoleId()).build()));
        return ent;
    }

    public RegisterResponse toDTO(UserEntity userEntity) {
        return RegisterResponse.builder()
                .firstName(userEntity.getFirstName())
                .build();
    }

    public AuthenticationResponse userDetailsAndJwt2LoginResponseDTO(UserDetails userInContext) {
        return AuthenticationResponse.builder()
                .email(userInContext.getUsername())
                .build();
    }


}