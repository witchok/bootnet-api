package com.witchok.bootnet.restControllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.witchok.bootnet.domain.users.UserDTO;
import com.witchok.bootnet.repositories.UserRepository;
import com.witchok.bootnet.services.UserService;
import com.witchok.bootnet.domain.users.User;
import com.witchok.bootnet.exceptions.UserNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static com.witchok.bootnet.UsersCreator.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @Test
    public void shouldCountUsers() throws Exception {
        long numberOfUsers = 3;
        when(userRepository.count())
                .thenReturn(numberOfUsers);

        mockMvc.perform(get("/users/number"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is( ((Long)numberOfUsers).intValue())));
    }

    @Test
    public void shouldReturnUserById() throws Exception{
        User user = new User(1,"user1","name1",
                "lastname1","em@email.com","password",null,
                new Date(), new LinkedHashSet<>(), new LinkedHashSet<>());

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/profiles/"+user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.username",is(user.getUsername())))
                .andExpect(jsonPath("$.name",is(user.getName())))
                .andExpect(jsonPath("$.lastName",is(user.getLastName())))
                .andExpect(jsonPath("$.password",is(user.getPassword())))
                .andExpect(jsonPath("$.email",is(user.getEmail())));
    }


    @Test
    public void shouldReturnNotFoundStatus() throws Exception{
        User user = null;

        when(userRepository.findById(1))
                .thenReturn(Optional.ofNullable(user));

        mockMvc.perform(get("/users/profiles/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnUsersSubscribers() throws Exception {
        int userId = 3;
        int startId = 5;
        int amount = 5;
        Set<User> subscribers = getFilledUserSet(startId, amount);
        when(userService.findSubscribersByUserId(3))
                .thenReturn(subscribers);

        mockMvc.perform(get("/users/profiles/"+userId+"/subscribers"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(amount)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(subscribers
                                    .stream()
                                    .map(item -> item.getId())
                                    .toArray()))
                );
    }

    @Test
    public void whenPerformSearchUsersSubscribers_shouldReturnNotFound() throws Exception {
        int id = 1;
        when(userService.findSubscribersByUserId(id))
                .thenThrow(new UserNotFoundException());

        mockMvc.perform(get("/users/profiles/"+id+"/subscribers"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnUsersSubscriptions() throws Exception {
        int userId = 3;
        int startId = 5;
        int amount = 5;
        Set<User> subscriptions = getFilledUserSet(startId, amount);
        when(userService.findSubscriptionsByUserId(3))
                .thenReturn(subscriptions);

        mockMvc.perform(get("/users/profiles/"+userId+"/subscriptions"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(amount)))
                .andExpect(jsonPath("$[*].id", containsInAnyOrder(subscriptions
                        .stream()
                        .map(item -> item.getId())
                        .toArray()))
                );
    }

    @Test
    public void whenPerformSearchUsersSubscriptions_shouldReturnNotFound() throws Exception {
        int id = 1;
        when(userService.findSubscriptionsByUserId(id))
                .thenThrow(new UserNotFoundException());

        mockMvc.perform(get("/users/profile/"+id+"/subscriptions"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldProcessRegistrationOfUser() throws Exception {
        UserDTO userRegisterDTO = UserDTO.builder()
                .username("usernew")
                .email("use@email.com")
                .password("password")
                .name("name")
                .lastName("lastname")
                .build();

        User savedUser = userRegisterDTO.convertToUser();
        savedUser.setId(3);
        savedUser.setCreatedAt(new Date());

        when(userService.registerNewUser(userRegisterDTO.convertToUser()))
                .thenReturn(savedUser);

        mockMvc.perform(post("/users/profiles")
//                .param("username",userRegisterDTO.getUsername())
//                .param("email",userRegisterDTO.getEmail())
//                .param("password",userRegisterDTO.getPassword())
//                .param("name",userRegisterDTO.getName())
//                .param("lastName",userRegisterDTO.getLastName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userRegisterDTO))
            ).andExpect(status().isCreated())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.username",is(userRegisterDTO.getUsername())))
            .andExpect(jsonPath("$.email",is(userRegisterDTO.getEmail())))
            .andExpect(jsonPath("$.password",is(userRegisterDTO.getPassword())))
            .andExpect(jsonPath("$.name",is(userRegisterDTO.getName())))
            .andExpect(jsonPath("$.lastName",is(userRegisterDTO.getLastName())))
            .andExpect(jsonPath("$.id",is(savedUser.getId())))
            .andExpect(jsonPath("$.createdAt",is(savedUser.getCreatedAt())));
    }

//    private static String asJsonString(UserDTO userDTO){
//        return String.format("{" +
//                "'username':%s," +
//                "''email':%s," +
//                "'password':%s," +
//                "'name':%s," +
//                "'lastname':%s}",userDTO.getUsername(),userDTO.getEmail(),
//                userDTO.getPassword(), userDTO.getName(), userDTO.getLastName());
//    }

    private static String asJsonString(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

}
