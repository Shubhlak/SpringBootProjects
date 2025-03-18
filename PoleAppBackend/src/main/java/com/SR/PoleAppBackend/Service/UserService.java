package com.SR.PoleAppBackend.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SR.PoleAppBackend.Repository.UserRepository;
import com.SR.PoleAppBackend.entity.User;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    
    // Retrieve all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Retrieve user by ID
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    // Create a new user
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Delete a user by ID
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
    
    
    // loign and when logied in change the status to true;
    
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            if (user.getLoggedInStatus() == null || !user.getLoggedInStatus()) {
                user.setLoggedInStatus(true);
                userRepository.save(user); // Save the updated status
            }
            return "Login successful";
        }
        throw new RuntimeException("Invalid credentials");
    }
}
