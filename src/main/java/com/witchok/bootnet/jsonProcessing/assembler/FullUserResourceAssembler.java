package com.witchok.bootnet.jsonProcessing.assembler;

import com.witchok.bootnet.domain.users.User;
import com.witchok.bootnet.jsonProcessing.resources.FullUserResource;
import com.witchok.bootnet.restControllers.UserController;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

//@Component
public class FullUserResourceAssembler extends BasicUserResourceAssembler<FullUserResource> {
    public FullUserResourceAssembler() {
        super(FullUserResource.class);
    }

    @Override
    protected FullUserResource instantiateResource(User entity) {
        return new FullUserResource(entity);
    }


}
