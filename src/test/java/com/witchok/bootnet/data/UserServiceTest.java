package com.witchok.bootnet.data;

import com.witchok.bootnet.domain.users.User;
import com.witchok.bootnet.exceptions.UserNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.MathContext;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static com.witchok.bootnet.UsersCreator.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackages = "com.witchok.bootnet.data")
public class UserServiceTest {
    @Autowired
    private UserService userService;
//
//    @Autowired
//    private TestEntityManager entityManager;

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
    public void shouldThrowUserNotFoundException(){
        int id =3;
        when(userRepository.findById(id))
                .thenReturn(Optional.ofNullable(null));
        Set<User> testSubscribers = userService.findSubscribersByUserId(id);
    }

//    @Test
//    public void shouldReturnUsersSubscribers(){
//        int id = 1;
//        User testUser = User.builder()
//                .username("user"+id)
//                .email("email"+id+"@mail.ru")
//                .password("password"+id)
//                .build();
//        new User("user"+id,"name"+id,"lastname"+id,
//                "email"+id+"@mail.ru","password"+id, null);
//        Set<User> subscribers = getFilledUserSet(id+1,5);
//        saveUsersIntoDb(subscribers);
//        subscribers.forEach((user) -> System.out.println(user.getId()+"--"+user.getUsername()));
//
//        testUser.setSubscribers(subscribers);
//        int savedUserId = (Integer) entityManager.persistAndGetId(testUser);
//        entityManager.flush();
//
//        Set<User> subscribersOfSavedUser = userService.findSubscribersByUserId(savedUserId);
//
//        assertThat(subscribersOfSavedUser, hasSize(subscribers.size()));
//        subscribersOfSavedUser.forEach(user -> System.out.println(user.getId()+"--"+user.getUsername()));
//        assertThat(subscribersOfSavedUser, equalTo(subscribers));
//    }



//    private void saveUsersIntoDb(Set<User> users){
//        users.stream()
//                .forEach(user -> user.setId((Integer) entityManager.persistAndGetId(user)));
//        entityManager.flush();
//    }
}

