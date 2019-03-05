package com.witchok.bootnet.domain.users;

import org.springframework.data.rest.core.config.Projection;

import java.util.Set;

@Projection(name = "subscriptions",types = {User.class})
public interface SubscriptionsProjection {

    String getUsername();
    String getName();
    String getLastName();
    String getEmail();
    String getProfileImage();
    Set<User> getSubscriptions();
}
