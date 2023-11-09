package com.ecomm_app.service;

import com.ecomm_app.model.JwtRequest;
import com.ecomm_app.model.Users;
import com.ecomm_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ResponseEntity<String> signUp(Users user)
    {
        try {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            userRepository.save(user);
            return ResponseEntity.ok("User registration successful");
        } catch (DataAccessException ex) {
            // Log the error or handle it as needed
            return ResponseEntity.badRequest().body("User registration failed: " + ex.getMessage());
        }

    }
    public void authenticate(UserDetails userDetails,JwtRequest jwtRequest)
    {
        System.out.println("raw :" +jwtRequest.getPassword()+ "hashd : "+userDetails.getPassword());
        boolean passwordCheck=passwordEncoder.matches(jwtRequest.getPassword(),userDetails.getPassword());
        System.out.println(passwordCheck);
        if (!passwordCheck)
        {
            throw new RuntimeException(" password not matching");
        }
    }

    public Users updateProfile(Users user)
    {

        Users exist=userRepository.findByEmail(user.getUsername()).orElse(null);
        if(exist!=null)
        {
            user.setUser_id(exist.getUser_id());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
        else {
            throw new RuntimeException("User doesn't exist");
        }
        return exist;
    }

    public ResponseEntity<Users> getProfile(String email)
    {
        Users exist=userRepository.findByEmail(email).orElse(null);
        return new ResponseEntity<>(exist, HttpStatus.OK);
    }
}
