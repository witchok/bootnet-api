package com.witchok.bootnet.jsonProcessing.resources;

import com.witchok.bootnet.domain.users.User;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import java.util.Date;

@Getter
public abstract class BasicUserResource extends ResourceSupport {
    private String username;

    private String name;

    private String lastName;

    private String email;

    private String profileImage;

    private Date createdAt;

    public BasicUserResource(User user){
        this.username = user.getUsername();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.profileImage = user.getProfileImage();
        this.createdAt = user.getCreatedAt();
    }
}
