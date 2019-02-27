package com.witchok.bootnet.domain.users;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.List;

public class UserResourceAssembler extends ResourceAssemblerSupport<User, UserResource> {
    public UserResourceAssembler(Class<?> controllerClass) {
        super(controllerClass, UserResource.class);
    }

    @Override
    protected UserResource instantiateResource(User entity) {
        return new UserResource(entity);
    }

    @Override
    public UserResource toResource(User user) {
        return createResourceWithId(user.getId(), user);
    }

}
