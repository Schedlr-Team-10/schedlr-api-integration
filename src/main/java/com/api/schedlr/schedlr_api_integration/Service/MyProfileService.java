package com.api.schedlr.schedlr_api_integration.Service;

import com.api.schedlr.schedlr_api_integration.entity.User;
import com.api.schedlr.schedlr_api_integration.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class MyProfileService {


    @Autowired
    private UserRepository userRepository;

    public User getUserById(int userid) {
        Optional<User> userOptional = userRepository.findByUserid(userid);

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new RuntimeException("User with userid " + userid + " not found");
        }
    }

    @Transactional
    public String updatePassword(int userId, String newPassword) {
        Optional<User> userOptional = userRepository.findByUserid(userId);
        if (userOptional != null) {
            User user = userOptional.get();
            user.setPassword(newPassword); // You may want to hash this password before saving
            userRepository.save(user); // Save the updated user
            return "Password updated successfully!";
        } else {
            return "User not found!";
        }
    }



}
