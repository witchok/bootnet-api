package com.witchok.bootnet.jsonProcessing.resources;

import com.witchok.bootnet.domain.users.User;
import lombok.Getter;
import org.springframework.hateoas.core.Relation;

@Getter
@Relation(value = "user", collectionRelation = "users")
public class UserWithoutSubResource extends BasicUserResource {

    public UserWithoutSubResource(User user) {
        super(user);
    }
}
