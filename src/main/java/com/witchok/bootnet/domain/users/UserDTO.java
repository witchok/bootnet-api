package com.witchok.bootnet.domain.users;

import lombok.Value;
import org.hibernate.annotations.Entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
public class UserDTO {

    @NotNull
    @Size(min = 2, max=40)
    private String username;
    @Size(min = 1, max=45)
    private String name;
    @Size(min = 1, max=45)
    private String lastName;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(min=6, max = 40)
    private String password;
    private String profileImage;
}
