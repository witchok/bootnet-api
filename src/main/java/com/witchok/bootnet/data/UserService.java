package com.witchok.bootnet.data;

import com.witchok.bootnet.domain.users.User;

import java.util.Set;

public interface UserService {
    Set<User> findSubscribersByUserId(int id);
    Set<User> findSubscriptionsByUserId(int id);
}
