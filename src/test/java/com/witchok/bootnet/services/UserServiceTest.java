package com.witchok.bootnet.services;

import com.witchok.bootnet.domain.users.User;
import com.witchok.bootnet.exceptions.UserNotFoundException;
import com.witchok.bootnet.exceptions.UserWithUsernameAlreadyExistsException;
import com.witchok.bootnet.repositories.UserRepository;
import com.witchok.bootnet.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static com.witchok.bootnet.UsersCreator.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackages = "com.witchok.bootnet.services")
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void shouldReturnSubscribers(){
        int id = 2;
        Set<User> subscribers = getFilledUserSet(id+10,5);
        User testUser = User.builder()
                .id(id)
                .username("user"+id)
                .email("email"+id+"@mail.ru")
                .password("password"+id)
                .subscribers(subscribers)
                .build();

        when(userRepository.findById(id))
                .thenReturn(Optional.of(testUser));

        Set<User> testSubscribers = userService.findSubscribersByUserId(id);

        assertThat(testSubscribers,hasSize(5));
        assertThat(testSubscribers, equalTo(subscribers));
    }

    @Test(expected = UserNotFoundException.class)
    public void searchingSubscribers_shouldThrowUserNotFoundException(){
        int id =3;
        when(userRepository.findById(id))
                .thenReturn(Optional.ofNullable(null));
        Set<User> testSubscribers = userService.findSubscribersByUserId(id);
    }

    @Test
    public void shouldReturnSubscriptions(){
        int id = 2;
        Set<User> subscriptions = getFilledUserSet(id+10,5);
        User testUser = User.builder()
                .id(id)
                .username("user"+id)
                .email("email"+id+"@mail.ru")
                .password("password"+id)
                .subscriptions(subscriptions)
                .build();

        when(userRepository.findById(id))
                .thenReturn(Optional.of(testUser));
        Set<User> testSubscriptions = userService.findSubscriptionsByUserId(id);
        assertThat(testSubscriptions, hasSize(5));
        assertThat(testSubscriptions, equalTo(subscriptions));
    }

    @Test(expected = UserNotFoundException.class)
    public void searchingSubscriptions_shouldThrowUserNotFoundException(){
        int id =3;
        when(userRepository.findById(id))
                .thenReturn(Optional.ofNullable(null));
        userService.findSubscriptionsByUserId(id);
    }

    @Test
    public void shouldRegisterNewUser(){
        User userToSave = User.builder()
                .username("newUser")
                .password("password")
                .email("email@gmail.com")
                .build();

        User userAfterSave = User.builder()
                .id(123)
                .username(userToSave.getUsername())
                .email(userToSave.getEmail())
                .password(userToSave.getPassword())
                .createdAt(new Date())
                .build();

        when(userRepository.findUserByEmail(userToSave.getEmail()))
                .thenReturn(Optional.ofNullable(null));

        when(userRepository.findUserByUsername(userToSave.getUsername()))
                .thenReturn(Optional.ofNullable(null));

        when(userRepository.save(userToSave))
                .thenReturn(userAfterSave);

        User savedUser = userService.registerNewUser(userToSave);

        assertNotNull(savedUser.getId());
        assertNotNull(savedUser.getCreatedAt());
        assertEquals(userToSave.getUsername(), savedUser.getUsername());
        assertEquals(userToSave.getEmail(), savedUser.getEmail());
        assertEquals(userToSave.getPassword(), savedUser.getPassword());
    }

    @Test(expected = UserWithUsernameAlreadyExistsException.class)
    public void shouldThrowUserWithUsernameAlreadyExistsEception(){
        User userToSave = User.builder()
                .username("newUser")
                .password("password")
                .email("email@gmail.com")
                .build();

        User matchedUser = new User();

        User userAfterSave = User.builder()
                .id(123)
                .username(userToSave.getUsername())
                .username(userToSave.getUsername())
                .password(userToSave.getPassword())
                .createdAt(new Date())
                .build();

        when(userRepository.findUserByEmail(userToSave.getEmail()))
                .thenReturn(Optional.ofNullable(null));

        when(userRepository.findUserByUsername(userToSave.getUsername()))
                .thenReturn(Optional.of(matchedUser));

        when(userRepository.save(userToSave))
                .thenReturn(userAfterSave);

        User savedUser = userService.registerNewUser(userToSave);
    }

    @Test(expected = UserWithUsernameAlreadyExistsException.class)
    public void shouldThrowUserWithEmailAlreadyExistsEception(){
        User userToSave = User.builder()
                .username("newUser")
                .password("password")
                .email("email@gmail.com")
                .build();

        User matchedUser = new User();

        User userAfterSave = User.builder()
                .id(123)
                .username(userToSave.getUsername())
                .username(userToSave.getUsername())
                .password(userToSave.getPassword())
                .createdAt(new Date())
                .build();

        when(userRepository.findUserByEmail(userToSave.getEmail()))
                .thenReturn(Optional.of(matchedUser));

        when(userRepository.findUserByUsername(userToSave.getUsername()))
                .thenReturn(Optional.ofNullable(null));

        when(userRepository.save(userToSave))
                .thenReturn(userAfterSave);

        User savedUser = userService.registerNewUser(userToSave);
    }

}

