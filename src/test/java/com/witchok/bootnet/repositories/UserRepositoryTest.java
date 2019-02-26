package com.witchok.bootnet.repositories;

import com.witchok.bootnet.domain.users.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
    private static final int NUMBER_OF_USERS = 5;
    private static User[] users;
    private static int[] ids;
    static {
        users = new User[NUMBER_OF_USERS];
        ids = new int[NUMBER_OF_USERS];
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;
    @Before
    public void populateDB(){
        System.out.println("@Before");
        for (int i = 0; i < NUMBER_OF_USERS; i++) {
            int id = i+1;
            users[i] = User.builder()
                    .username("user"+id)
                    .name("name"+id)
                    .lastName("lastname"+id)
                    .email("email"+id+"@mail.ru")
                    .password("password"+id)
                .build();
        }
        for (int i = 0; i < users.length; i++) {
            ids[i] = (Integer) entityManager.persistAndGetId(users[i]);
        }
        entityManager.flush();
    }


    @Test
    public void shouldCountUsers(){
        long numberOfUsers = userRepository.count();
        assertEquals(NUMBER_OF_USERS,numberOfUsers);
        entityManager.clear();
    }

    @Test
    public void shouldFindUserById(){
        Optional<User> optUser = userRepository.findById(ids[0]);
        assertTrue(optUser.isPresent());
        User user1 = optUser.get();
        assertEquals(users[0].getUsername(), user1.getUsername());
    }

    @Test
    public void shouldNotFindUserById(){
        Optional<User> nonExistedUser = userRepository.findById(ids[ids.length-1]+1);
        assertFalse(nonExistedUser.isPresent());
    }

    @Test
    public void shouldSaveNewUser(){
        User userToSave = User.builder()
                .username("newUser")
                .password("password")
                .email("emaNew@ema.com")
                .build();
        User savedUser = userRepository.save(userToSave);

        assertNotNull(savedUser.getId());
        Optional<User> userRepositoryByIdOpt = userRepository.findById(savedUser.getId());
        assertTrue(userRepositoryByIdOpt.isPresent());
        User userById = userRepositoryByIdOpt.get();

        assertEquals(savedUser,userById);
        assertEquals(userById.getUsername(), userToSave.getUsername());
        assertEquals(userById.getPassword(), userToSave.getPassword());
        assertEquals(userById.getEmail(), userToSave.getEmail());
        assertNotNull(userById.getCreatedAt());
    }

    @Test
    public void shouldFindUserByUsername(){
        Optional<User> optUser = userRepository.findUserByUsername(users[0].getUsername());
        assertTrue(optUser.isPresent());
        User user = optUser.get();
        assertEquals(users[0].getId(), user.getId());
        assertEquals(users[0].getEmail(), user.getEmail());
    }


    @Test
    public void shouldNotFindUserByUsername(){
        Optional<User> optUser = userRepository.findUserByUsername("nonExisted");
        assertFalse(optUser.isPresent());
    }


    @Test
    public void shouldFindUserByEmail(){
        Optional<User> optUser = userRepository.findUserByEmail(users[0].getEmail());
        assertTrue(optUser.isPresent());
        User user = optUser.get();
        assertEquals(users[0].getId(), user.getId());
        assertEquals(users[0].getUsername(), user.getUsername());
    }


    @Test
    public void shouldNotFindUserByEmail(){
        Optional<User> optUser = userRepository.findUserByEmail("nonExisted@email.com");
        assertFalse(optUser.isPresent());
    }
}
