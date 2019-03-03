package com.witchok.bootnet.jsonProcessing.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.witchok.bootnet.domain.users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class CustomSetUserSerializer extends StdSerializer<Set<User>> {

    public CustomSetUserSerializer(){
        this(null);
    }

    public CustomSetUserSerializer(Class<Set<User>> t) {
        super(t);
    }

    @Override
    public void serialize(Set<User> users, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        Set<UserForJson> usersForSerialize = new HashSet<>();
        for (var user: users) {
            usersForSerialize.add(
                    new UserForJson(user));
        }
        jsonGenerator.writeObject(usersForSerialize);
    }

    @Value
    @AllArgsConstructor
    @Builder
    private static class UserForJson{
        private Integer id;
        private String username;
        private String name;
        private String lastName;
        private String email;
        private String profileImage;
        private Date createdAt;

        public UserForJson(User user){
            this(user.getId(),
                    user.getUsername(),
                    user.getName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getProfileImage(),
                    user.getCreatedAt());
        }
    }
}

