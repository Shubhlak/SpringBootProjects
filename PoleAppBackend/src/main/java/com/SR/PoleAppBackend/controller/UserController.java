package com.SR.PoleAppBackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.SR.PoleAppBackend.DTO.LoginResponse;
import com.SR.PoleAppBackend.Service.UserService;
import com.SR.PoleAppBackend.entity.User;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;


  
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(response);
    }
    

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.ok(createdUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Logout endpoint
    @PostMapping("/logout/{userId}")
    public ResponseEntity<String> logoutUser(@PathVariable Long userId) {
        userService.logout(userId);
        return ResponseEntity.ok("Logout successful");
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long userId, @RequestBody User updatedUser) {
        User user = userService.getUserById(userId);
        user.setFullName(updatedUser.getFullName());
        User savedUser = userService.updateUser(user);
        return ResponseEntity.ok(savedUser);
    }
    
 
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        try {
            userService.generateOtpAndSendEmail(email);
            return ResponseEntity.ok("OTP sent to " + email + " if it exists in our system.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
 
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        boolean valid = userService.verifyOtp(email, otp);
        if (valid) {
            return ResponseEntity.ok("OTP is valid. You can now reset your password.");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired OTP.");
        }
    }
   
  
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email,
                                                @RequestParam String otp,
                                                @RequestParam String newPassword) {
        try {
            userService.resetPassword(email, otp, newPassword);
            return ResponseEntity.ok("Password reset successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

   
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}


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