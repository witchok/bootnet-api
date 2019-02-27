package com.witchok.bootnet.domain.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.witchok.bootnet.serialier.CustomSetUserSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
public class UserResource extends ResourceSupport {

    private String username;

    private String name;

    private String lastName;

    private String email;

    private String password;

    private String profileImage;

    private Date createdAt;

    private Set<User> subscribers;

    private Set<User> subscriptions;

    public UserResource (User user){
        this.username = user.getUsername();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.profileImage = user.getProfileImage();
        this.createdAt = user.getCreatedAt();
        this.subscribers = user.getSubscribers();
        this.subscriptions = user.getSubscriptions();
    }
}
