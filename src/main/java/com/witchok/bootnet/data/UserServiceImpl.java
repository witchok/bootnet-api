package com.witchok.bootnet.data;

import com.witchok.bootnet.domain.users.User;
import com.witchok.bootnet.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    public Set<User> findSubscribersByUserId(int id){
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isPresent()){
            User user = optUser.get();
            return user.getSubscribers();
        }
        throw new UserNotFoundException(String.format("User with id '%d' isn't found",id));
    }

    @Override
    public Set<User> findSubscriptionsByUserId(int id) {
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isPresent()){
            User user = optUser.get();
            return user.getSubscriptions();
        }
        throw new UserNotFoundException(String.format("User with id '%d' isn't found",id));
    }
}
