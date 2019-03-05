package com.witchok.bootnet.domain.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.witchok.bootnet.domain.users.User;
import org.springframework.data.rest.core.config.Projection;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.Set;

@Projection(name = "subscribers",types = {User.class})
public interface SubscribersProjection {

    String getUsername();
    String getName();
    String getLastName();
    String getEmail();
    String getProfileImage();
    Set<User> getSubscribers();
    Set<User> getSubscriptions();
}
