package com.aa.repository;

import com.aa.SpringBootJwtApplication;
import com.aa.domain.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class RoleRepositoryTest {
    @Autowired
    private RoleRepository repo;

    @Test
    void saveAll(){
        Role admin = new Role("ADMIN");
        Role editor = new Role("EDITOR");
        Role user = new Role("USER");

        List<Role> savedRoles = repo.saveAll(List.of(admin, editor, user));
        assertNotNull(savedRoles);
        assertEquals(3, savedRoles.size());
    }
}
