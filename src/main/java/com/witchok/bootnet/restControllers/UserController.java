package com.witchok.bootnet.restControllers;

import com.witchok.bootnet.domain.messages.ErrorMessage;
import com.witchok.bootnet.domain.users.UserDTO;
import com.witchok.bootnet.domain.users.UserResource;
import com.witchok.bootnet.domain.users.UserResourceAssembler;
import com.witchok.bootnet.exceptions.UserProcessingException;
import com.witchok.bootnet.exceptions.UserWithEmailAlreadyExistsException;
import com.witchok.bootnet.exceptions.UserWithUsernameAlreadyExistsException;
import com.witchok.bootnet.repositories.UserRepository;
import com.witchok.bootnet.services.UserService;
import com.witchok.bootnet.domain.users.User;
import com.witchok.bootnet.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Slf4j
@RestController
@RequestMapping(path = "/users", produces = "application/json")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/number")
    public long numberOfUsers(){
        log.info("numberOfUsers method");
        long number = userRepository.count();
        log.info("numberOfUsers method - number={}",number);
        return number;

    }

    @GetMapping("/profiles/{id}")
    public User userById (@PathVariable("id") int id){
        log.info("userById method, id={}",id);
        User user = userService.findUserById(id);
        log.info("User '{}' was found in db",user.getUsername());
        return user;
    }

    @GetMapping("/profiles/{id}/subscribers")
    public Set<User> getUserSubscribers(@PathVariable("id") int id){
        log.info("getUserSubscribers method, id={}",id);
        Set<User> subscribers = userService.findSubscribersByUserId(id);

        List<UserResource> userResources = new UserResourceAssembler(UserController.class)
                .toResources(subscribers);
        Resources<UserResource> subscriberResources = new Resources<>(userResources);
        subscriberResources
                .add(linkTo(methodOn(UserController.class).getUserSubscribers(id))
                        .withRel("subscribers"));

        log.info("subscribers size = {}",subscribers.size());
        return subscribers;
    }

    @GetMapping("/profiles/{id}/subscriptions")
    public Set<User> getUserSubscriptions(@PathVariable("id") int id){
        log.info("getUserSubscriptions method, id={}",id);
        Set<User> subscriptions = userService.findSubscriptionsByUserId(id);
        log.info("subscriptions size = {}",subscriptions.size());
        return subscriptions;
    }

    @PostMapping(value = "/profiles", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public User saveNewUser(
            @RequestBody UserDTO userDTO,
            Errors errors){
        //TODO verify user
        log.info("saveNewUser: trying save user with username '{}'",userDTO.getUsername());
        User userToSave = userDTO.convertToUser();
        User savedUser = userService.registerNewUser(userToSave);
        log.info("saveNewUser: User '{}' saved successfully, id is '{}'",savedUser.getUsername(), savedUser.getId());
        return savedUser;
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage userNotFoundHandler(UserProcessingException exc){
        log.info("userNotFoundHandler: {}",exc.getMessage());
        return new ErrorMessage(exc.getMessage());
    }

    @ExceptionHandler(value = {UserWithUsernameAlreadyExistsException.class,
            UserWithEmailAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage userAlreadyExistsExceptionHandler(UserProcessingException exc){
        log.info("userAlreadyExistsExceptionHandler: {}",exc.getMessage());
        return new ErrorMessage(exc.getMessage());
    }
}
