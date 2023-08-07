package com.aa.service;

import com.aa.domain.Role;
import com.aa.domain.User;
import com.aa.exception.DuplicateEmailException;
import com.aa.exception.UserNotFoundException;
import com.aa.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repo;

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
    void listAll(){
        when(repo.findAll()).thenReturn(List.of(admin, editor, user));

        List<User> listUsers = service.listAll();
        assertNotNull(listUsers);
        assertEquals(3, listUsers.size());

        listUsers.forEach(System.out::println);
    }

    @Test
    void save() throws DuplicateEmailException {
        when(repo.save(any(User.class))).thenReturn(admin);
        User savedUser = service.save(admin);
        assertNotNull(savedUser);
        assertEquals(admin.getEmail(), savedUser.getEmail());
    }

    @Test
    void saveForException(){
        String email = admin.getEmail();
        user.setEmail(email);

        when(repo.findByEmail(email)).thenReturn(admin);
        assertThrows(DuplicateEmailException.class, () -> {
            service.save(user);
        });
    }

    @Test
    void get() throws UserNotFoundException {
        Integer id = 1;
        when(repo.findById(id)).thenReturn(Optional.of(admin));

        User findedUser = service.get(id);
        assertNotNull(user);
        assertEquals(findedUser.getFirstName(), admin.getFirstName());
    }

    @Test
    void getForException(){
        Integer id = 10;
        when(repo.findById(id)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> {
            service.get(id);
        });
    }

    @Test
    void delete() throws UserNotFoundException {
        Integer id = 1;
        when(repo.findById(id)).thenReturn(Optional.of(admin));

        doNothing().when(repo).deleteById(id);
        service.delete(id);
        verify(repo, times(1)).deleteById(id);
    }

    @Test
    void deleteForException(){
        Integer id = 1;
        when(repo.findById(id)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> {
            service.delete(id);
        });
    }

    @Test
    void findByEmail() throws UserNotFoundException {
        String email = "admin@gmail.com";
        when(repo.findByEmail(email)).thenReturn(admin);
        User findedUser = service.findByEmail(email);
        assertNotNull(findedUser);
    }

    @Test
    void findByEmailForException(){
        String email = "notfound@gmail.com";
        when(repo.findByEmail(email)).thenThrow(new NoSuchElementException());
        assertThrows(UserNotFoundException.class, () ->{
            service.findByEmail(email);
        });
    }
}
