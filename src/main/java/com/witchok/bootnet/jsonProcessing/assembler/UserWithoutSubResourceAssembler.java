package com.witchok.bootnet.jsonProcessing.assembler;

import com.witchok.bootnet.domain.users.User;
import com.witchok.bootnet.jsonProcessing.resources.UserWithoutSubResource;
import com.witchok.bootnet.restControllers.UserController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

//@Component
public class UserWithoutSubResourceAssembler extends BasicUserResourceAssembler<UserWithoutSubResource> {
    public UserWithoutSubResourceAssembler() {
        super(UserWithoutSubResource.class);
    }

    @Override
    protected UserWithoutSubResource instantiateResource(User user) {
        return new UserWithoutSubResource(user);
    }

}
