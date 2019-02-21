package com.witchok.bootnet.restControllers;

import com.witchok.bootnet.data.UserRepository;
import com.witchok.bootnet.domain.users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/users", produces = "application/json")
@CrossOrigin(origins = "*")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/number")
    public long numberOfUsers(){
        log.info("numberOfUsers method");
        long number = userRepository.count();
        log.info("numberOfUsers method - number={}",number);
        return number;

    }

    @GetMapping("/profile")
    public ResponseEntity<User> userById (@RequestParam("id") int id){
        log.info("userById method, id={}",id);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            log.info("User '{}' was found in db",optionalUser.get().getUsername());
            return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
        }
        log.info("User wasn't found in db");
        return new ResponseEntity<>((User) null, HttpStatus.NOT_FOUND);
    }

}
