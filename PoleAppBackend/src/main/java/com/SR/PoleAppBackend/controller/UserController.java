package com.SR.PoleAppBackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.SR.PoleAppBackend.Service.UserService;
import com.SR.PoleAppBackend.entity.User;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * GET: Retrieve all users
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    
    
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }
    
    
    // this is the end point for the register method
    
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.createUser(user);
    }
    
    
    
    
    
    // this creates a new user 
    
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
   

    /**
     * GET: Retrieve user by ID
     */
    @GetMapping("/{id:\\d+}")
    public User getUserById(@PathVariable("id") Long userId) {
        return userService.getUserById(userId);
    }


    /**
     * DELETE: Delete a user by ID
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
    }
      
}



// this class will hanle the login process 

class LoginRequest {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
