package com.aa.resource;

import com.aa.domain.Role;
import com.aa.domain.User;
import com.aa.exception.DuplicateEmailException;
import com.aa.exception.UserNotFoundException;
import com.aa.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class UserRestControllerTest {
    private static final String URI = "/api/v1/users";
    @MockBean private UserService service;
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper mapper;
    private User admin;
    private User editor;
    private User user;

    @BeforeEach
    void init(){
        String password = "password";

        admin = new User("admin", "admin", "admin@gmail.com", password, true, true, new Role(1));
        editor = new User("editor", "editor", "editor@gmail.com", password, true, true, new Role(2));
        user = new User("user", "user", "user@gmail.com", password, true, true, new Role(3));
    }

    @Test
    void listAll() throws Exception {
        when(service.listAll()).thenReturn(List.of(admin, editor, user));
        mockMvc.perform(get(URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void getUser() throws Exception {
        Integer id = 1;
        when(service.get(id)).thenReturn(admin);
        mockMvc.perform(get(URI+"/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(admin.getFirstName())));
    }

    @Test
    void save() throws Exception {
        when(service.save(any(User.class))).thenReturn(admin);
        mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(admin)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(admin.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(admin.getLastName())))
                .andExpect(jsonPath("$.email", is(admin.getEmail())));
    }

    @Test
    void update() throws Exception {
        String email = "new_admin@gmail.com";
        admin.setEmail(email);
        when(service.save(any(User.class))).thenReturn(admin);
        mockMvc.perform(put(URI).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(admin)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(email)));
    }

    @Test
    void testDelete() throws Exception {
        Integer id = 1;
        doNothing().when(service).delete(id);
        mockMvc.perform(delete(URI+ "/{id}", id))
                .andExpect(status().isNoContent());
        verify(service, times(1)).delete(id);
    }
}
