package com.witchok.bootnet.restControllers;

import com.witchok.bootnet.domain.messages.ErrorMessage;
import com.witchok.bootnet.domain.users.UserDTO;
import com.witchok.bootnet.exceptions.UserProcessingException;
import com.witchok.bootnet.exceptions.UserWithEmailAlreadyExistsException;
import com.witchok.bootnet.exceptions.UserWithUsernameAlreadyExistsException;
import com.witchok.bootnet.jsonProcessing.assembler.FullUserResourceAssembler;
import com.witchok.bootnet.jsonProcessing.assembler.UserWithoutSubResourceAssembler;
import com.witchok.bootnet.jsonProcessing.resources.UserWithoutSubResource;
import com.witchok.bootnet.repositories.UserRepository;
import com.witchok.bootnet.services.UserService;
import com.witchok.bootnet.domain.users.User;
import com.witchok.bootnet.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Slf4j
@RepositoryRestController
//@RequestMapping(produces = "application/json")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private static FullUserResourceAssembler fullUserResourceAssembler
            = new FullUserResourceAssembler();

    private static UserWithoutSubResourceAssembler userWithoutSubResourceAssembler
            = new UserWithoutSubResourceAssembler();

    @GetMapping(value = "/users/{id}/subscribers", produces = "application/hal+json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Resources<UserWithoutSubResource> getUserSubscribers(@PathVariable("id") int id){
        log.info("getUserSubscribers method, id={}",id);
        Set<User> subscribers = userService.findSubscribersByUserId(id);

        List<UserWithoutSubResource> userResources = userWithoutSubResourceAssembler
                .toResources(subscribers);
        Resources<UserWithoutSubResource> subscriberResources = new Resources<>(userResources);
        subscriberResources
                .add(linkTo(methodOn(UserController.class).getUserSubscribers(id))
                        .withRel("subscribers"));
        log.info("subscribers size = {}",subscribers.size());
        return subscriberResources;
    }

    @GetMapping(value = "/users/{id}/subscriptions", produces = "application/hal+json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Resources<UserWithoutSubResource> getUserSubscriptions(@PathVariable("id") int id){
        log.info("getUserSubscriptions: id={}",id);
        Set<User> subscriptions = userService.findSubscriptionsByUserId(id);

        List<UserWithoutSubResource> userResources = userWithoutSubResourceAssembler
                .toResources(subscriptions);
        Resources<UserWithoutSubResource> subscriberResources = new Resources<>(userResources);
        subscriberResources
                .add(linkTo(methodOn(UserController.class).getUserSubscribers(id))
                        .withRel("subscribers"));

        log.info("subscriptions size = {}",subscriptions.size());
        return subscriberResources;
    }

    @GetMapping(value = "/users/{id}", produces = "application/hal+json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResourceSupport userById (@PathVariable("id") int id,
                                     @RequestParam(value = "wholeData", defaultValue = "false") Boolean wholeData){
        log.info("userById method, id={}",id);
        User user = userService.findUserById(id);
        log.info("User '{}' was found in db",user.getUsername());
        if(wholeData){
            return fullUserResourceAssembler.toResource(user);
        }
        return userWithoutSubResourceAssembler.toResource(user);
    }

    @PostMapping(value = "/users", consumes = "application/json", produces ="application/hal+json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserWithoutSubResource saveNewUser(
            @RequestBody UserDTO userDTO,
            Errors errors){
        //TODO verify user
        log.info("saveNewUser: trying save user with username '{}'",userDTO.getUsername());
        User userToSave = userDTO.convertToUser();
        User savedUser = userService.registerNewUser(userToSave);
        log.info("saveNewUser: User '{}' saved successfully, id is '{}'",savedUser.getUsername(), savedUser.getId());
        UserWithoutSubResource userResource = userWithoutSubResourceAssembler.toResource(savedUser);
        return userResource;
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessage userNotFoundHandler(UserProcessingException exc){
        log.info("userNotFoundHandler: {}",exc.getMessage());
        return new ErrorMessage(exc.getMessage());
    }

    @ExceptionHandler(value = {UserWithUsernameAlreadyExistsException.class,
            UserWithEmailAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorMessage userAlreadyExistsExceptionHandler(UserProcessingException exc){
        log.info("userAlreadyExistsExceptionHandler: {}",exc.getMessage());
        return new ErrorMessage(exc.getMessage());
    }
}
