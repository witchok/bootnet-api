package com.witchok.bootnet.services;

import com.witchok.bootnet.domain.users.User;
import com.witchok.bootnet.exceptions.UserNotFoundException;
import com.witchok.bootnet.exceptions.UserWithUsernameAlreadyExistsException;
import com.witchok.bootnet.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    public Set<User> findSubscribersByUserId(int id){
        log.info("findSubscribersByUserId: id = {}",id);
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isPresent()){
            User user = optUser.get();
            log.info("findSubscribersByUserId: user with id '{}' was found, username is '{}'",id,user.getUsername());
            Set<User> subscribers = user.getSubscribers();
            log.info("findSubscribersByUserId: number of subscribers is {}",subscribers.size());
            return subscribers;
        }
        log.info("findSubscribersByUserId: user with id '{}' wasn't found",id);
        throw new UserNotFoundException(String.format("User with id '%d' isn't found",id));
    }

    @Override
    public Set<User> findSubscriptionsByUserId(int id) {
        log.info("findSubscriptionsByUserId: id = {}",id);
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isPresent()){
            User user = optUser.get();
            log.info("findSubscriptionsByUserId: user with id '{}' was found, username is '{}'",id,user.getUsername());
            Set<User> subscriptions = user.getSubscriptions();
            log.info("findSubscriptionsByUserId: number of subscriptions is {}",subscriptions.size());
            return subscriptions;
        }
        log.info("findSubscriptionsByUserId: user with id '{}' wasn't found",id);
        throw new UserNotFoundException(String.format("User with id '%d' isn't found",id));
    }

    @Override
    public User registerNewUser(User user) {
        log.info("registerNewUser: username is '{}'", user.getUsername());
        if (userRepository.findUserByUsername(user.getUsername()).isPresent()){
            log.info("registerNewUser: User with username '{}' already exists",user.getUsername());
            throw new UserWithUsernameAlreadyExistsException(
                    String.format("User with username '%s' already exists", user.getUsername()));
        }
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()){
            log.info("registerNewUser: User with email '{}' already exists",user.getEmail());
            throw new UserWithUsernameAlreadyExistsException(
                    String.format("User with email '%s' already exists", user.getEmail()));
        }
        User savedUser = userRepository.save(user);
        log.info("registerNewUser: user saved successfully with id '{}'",savedUser.getId());
        return savedUser;
    }
}
