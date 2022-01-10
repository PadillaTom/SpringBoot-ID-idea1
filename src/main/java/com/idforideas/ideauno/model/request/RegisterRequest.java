package com.idforideas.ideauno.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {

   private String firstName;

    private String lastName;

    private String email;

    private String password;

    private int age;

    private String gender;

    private String nationality;

    private Integer roleId;

}