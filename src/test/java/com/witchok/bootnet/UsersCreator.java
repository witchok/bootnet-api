package com.witchok.bootnet;

import com.witchok.bootnet.domain.users.User;

import java.util.HashSet;
import java.util.Set;

public abstract class UsersCreator {
    public static Set<User> getFilledUserSet(int startId, int amount){
        Set<User> users = new HashSet<>(amount);
        for (int i = 0; i < amount; i++) {
            int id = ++startId;
            users.add(User.builder()
                    .id(id)
                    .username("user"+id)
                    .email("email"+id+"@mail.ru")
                    .password("password"+id)
                    .build());
        }
        return users;
    }
}
