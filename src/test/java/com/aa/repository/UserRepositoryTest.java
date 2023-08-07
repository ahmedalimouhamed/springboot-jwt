package com.aa.repository;

import com.aa.domain.Role;
import com.aa.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void saveAll(){
        String password = "password";

        String encodedPassword = passwordEncoder.encode(password);

        User admin = new User("admin", "admin", "admin@gmail.com", encodedPassword, true, true, new Role(1));
        User editor = new User("editor", "editor", "editor@gmail.com", encodedPassword, true, true, new Role(2));
        User user = new User("user", "user", "user@gmail.com", encodedPassword, true, true, new Role(3));

        List<User> savedUsers = repo.saveAll(List.of(admin, editor, user));
        assertNotNull(savedUsers);
        assertEquals(3, savedUsers.size());
    }

    @Test
    void findTestByEmail(){
        String email = "admin@gmail.com";
        User user = repo.findByEmail(email);
        assertNotNull(user);
        System.out.println(user);
    }
}
