package com.sofka.cuentaBancaria.controller;

import com.sofka.cuentaBancaria.model.User;
import com.sofka.cuentaBancaria.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try{
            userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created succesful");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<String> getUser(@PathVariable("id") int id) {
        try{
            User user=userService.getUser(id);
            return ResponseEntity.status(HttpStatus.OK).body(user.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
