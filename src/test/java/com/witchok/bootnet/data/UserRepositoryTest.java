package com.witchok.bootnet.data;

import com.witchok.bootnet.domain.users.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;

import static org.junit.Assert.*;

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
            users[i] = new User("user"+id,"name"+id,"lastname"+id,
                    "email"+id+"@mail.ru","password"+id);
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

        Optional<User> nonExistedUser = userRepository.findById(ids[ids.length-1]+1);
        assertFalse(nonExistedUser.isPresent());
    }
}
