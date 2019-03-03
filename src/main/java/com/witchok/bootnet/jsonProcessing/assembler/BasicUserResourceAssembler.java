package com.witchok.bootnet.jsonProcessing.assembler;

import com.witchok.bootnet.domain.users.User;
import com.witchok.bootnet.jsonProcessing.resources.BasicUserResource;
import com.witchok.bootnet.restControllers.UserController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public abstract class BasicUserResourceAssembler<T extends BasicUserResource>
        extends ResourceAssemblerSupport<User,T> {
//    @Value("${spring.data.rest.base-path}")
//    private String basePath;
//
    public BasicUserResourceAssembler(Class<T> resourceType) {
        super(UserController.class, resourceType);
    }

    @Override
    protected abstract T instantiateResource(User entity);

    @Override
    public T toResource(User user) {
        T userResource = instantiateResource(user);

        userResource.add(linkTo(methodOn(UserController.class).userById(user.getId(),false))
                .withRel("self"));

        userResource.add(linkTo(methodOn(UserController.class).getUserSubscribers(user.getId()))
                .withRel("subscribers"));

        userResource.add(linkTo(methodOn(UserController.class).getUserSubscriptions(user.getId()))
                .withRel("subscriptions"));

        userResource.add(linkTo(methodOn(UserController.class).userById(user.getId(),true))
                .withRel("selfFull"));
        return userResource;
    }
}
