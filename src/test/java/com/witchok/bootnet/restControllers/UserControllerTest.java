package com.witchok.bootnet.restControllers;

import com.witchok.bootnet.data.UserRepository;
import com.witchok.bootnet.domain.users.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

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
                "lastname1","em@email.com","password",null, new Date());

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/profile?id="+user.getId()))
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
    public void shoulReturnNotFoundStatus() throws Exception{
        User user = null;


        when(userRepository.findById(1))
                .thenReturn(Optional.ofNullable(user));

        mockMvc.perform(get("/users/profile?id="+1))
                .andExpect(status().isNotFound());
    }
}
