package com.witchok.bootnet.domain.users;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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

    public User convertToUser(){
        return User.builder()
                .username(this.username)
                .name(this.name)
                .lastName(this.lastName)
                .email(this.email)
                .password(this.password)
                .profileImage(this.profileImage)
                .build();
    }

}
