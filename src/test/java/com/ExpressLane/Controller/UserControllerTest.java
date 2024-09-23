package com.ExpressLane.Controller;

import com.ExpressLane.DTO.UserDTO;
import com.ExpressLane.Service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;

import static java.lang.reflect.Array.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createUser() {
    }

    @Test
    @WithMockUser(roles = "ROLE_ADMIN") // Simula un usuario con rol ADMIN
    void getAllUsers() throws Exception {
        List<UserDTO> userList = new ArrayList<>();
        userList.add(new UserDTO()); // Agrega un UserDTO de prueba

        when(userService.getAllUsers()).thenReturn(userList);

        mockMvc.perform((RequestBuilder) get("/api/users"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.length()").value(userList.size()));

        verify(userService, times(1)).getAllUsers();
    }

    private Object get(String path) {
        return null;
    }

    @Test
    void getUserById() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }
}