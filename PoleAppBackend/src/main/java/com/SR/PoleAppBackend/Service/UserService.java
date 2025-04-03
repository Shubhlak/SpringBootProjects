package com.SR.PoleAppBackend.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.mindrot.jbcrypt.BCrypt; // Added import for BCrypt hashing
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SR.PoleAppBackend.DTO.EmailDetails;
import com.SR.PoleAppBackend.DTO.LoginResponse;
import com.SR.PoleAppBackend.Repository.UserRepository;
import com.SR.PoleAppBackend.entity.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EmailService emailService;
    
 
    private static final int OTP_VALID_DURATION = 5;
    

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    
    public User createUser(User user) {
       
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new RuntimeException("Email already in use");
        }
        
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(hashedPassword);
        
        return userRepository.save(user);
    }

    
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
    
    
    public LoginResponse login(String email, String password) {
        User user = userRepository.findByEmail(email);
       
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            if (user.getLoggedInStatus() == null || !user.getLoggedInStatus()) {
                user.setLoggedInStatus(true);
                userRepository.save(user); 
            }
            return new LoginResponse("Login successful", user);
        }
        throw new RuntimeException("Invalid credentials");
    }
    
    
    public void logout(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        user.setLoggedInStatus(false);  
        userRepository.save(user);
    }
    
   
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    
    
    
    public void generateOtpAndSendEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("No user found with email: " + email);
        }

        
        String otp = String.format("%06d", new Random().nextInt(999999));

        
        user.setResetOtp(otp);
        user.setResetOtpExpiry(LocalDateTime.now().plusMinutes(OTP_VALID_DURATION));
        userRepository.save(user);

       
        String subject = "Password Reset OTP";
        String msgBody = "Hello " + user.getFullName() + ",\n\n"
                + "Your OTP for password reset is: " + otp + "\n"
                + "This OTP will expire in " + OTP_VALID_DURATION + " minutes.\n\n"
                + "If you did not request a password reset, please ignore this email.\n\n"
                + "Regards,\nPollApp Team 4 ";
        EmailDetails details = new EmailDetails(email, msgBody, subject, null);
        emailService.sendSimpleMail(details);
    }
    
  
    
    public boolean verifyOtp(String email, String otp) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }

        
        if (user.getResetOtp() == null || user.getResetOtpExpiry() == null) {
            return false;
        }
        if (!user.getResetOtp().equals(otp)) {
            return false;
        }
        if (LocalDateTime.now().isAfter(user.getResetOtpExpiry())) {
            return false; 
        }

        return true;
    }
    

    public void resetPassword(String email, String otp, String newPassword) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("No user found with email: " + email);
        }

        
        if (!verifyOtp(email, otp)) {
            throw new RuntimeException("Invalid or expired OTP.");
        }

        
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));
        user.setPassword(hashedPassword);

        
        user.setResetOtp(null);
        user.setResetOtpExpiry(null);

        userRepository.save(user);
    }
}