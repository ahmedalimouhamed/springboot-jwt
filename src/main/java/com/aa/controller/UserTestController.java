package com.aa.controller;

import com.aa.domain.User;
import com.aa.exception.DuplicateEmailException;
import com.aa.exception.UserNotFoundException;
import com.aa.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserTestController {
    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<List<User>> listAll(){
        List<User> listUsers = service.listAll();
        if(listUsers.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<List<User>>(listUsers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable("id") Integer id) throws UserNotFoundException {
        return new ResponseEntity<User>(service.get(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody @Valid User user) throws DuplicateEmailException {
        User savedUser = service.save(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(uri).body(savedUser);
    }

    @PutMapping
    public ResponseEntity<User> update(@RequestBody @Valid User user) throws DuplicateEmailException {
        return new ResponseEntity<User>(service.save(user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) throws UserNotFoundException {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
