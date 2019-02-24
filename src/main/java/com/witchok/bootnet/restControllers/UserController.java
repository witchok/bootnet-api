package com.witchok.bootnet.restControllers;

import com.witchok.bootnet.data.UserRepository;
import com.witchok.bootnet.data.UserService;
import com.witchok.bootnet.domain.users.User;
import com.witchok.bootnet.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(path = "/users", produces = "application/json")
@CrossOrigin(origins = "*")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

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

    @GetMapping("/profile/{id}")
    public ResponseEntity<User> userById (@PathVariable("id") int id){
        log.info("userById method, id={}",id);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            log.info("User '{}' was found in db",optionalUser.get().getUsername());
            return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
        }
        log.info("User wasn't found in db");
        return new ResponseEntity<>((User) null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/profile/{id}/subscribers")
    public Set<User> getuUserSubscribers(@PathVariable("id") int id){
        log.info("getUserSubscribers method, id={}",id);
        Set<User> subscribers = userService.findSubscribersByUserId(id);
        return subscribers;
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void userNotFoundHandler(){ }
}
