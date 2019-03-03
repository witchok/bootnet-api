package com.witchok.bootnet.jsonProcessing.resources;

import com.witchok.bootnet.domain.users.User;
import com.witchok.bootnet.jsonProcessing.assembler.UserWithoutSubResourceAssembler;
import lombok.Getter;
import org.springframework.hateoas.core.Relation;

import java.util.List;

@Getter
@Relation(value = "user", collectionRelation = "users")
public class FullUserResource extends BasicUserResource {
    private static final UserWithoutSubResourceAssembler subUserAssembler = new UserWithoutSubResourceAssembler();

    private List<UserWithoutSubResource> subscribers;

    private List<UserWithoutSubResource> subscriptions;

    public FullUserResource(User user){
        super(user);
        this.subscribers =  subUserAssembler.toResources(user.getSubscribers());
        this.subscriptions = subUserAssembler.toResources(user.getSubscriptions());
    }
}
