package com.ecomm_app.service;

import com.ecomm_app.dto.JwtRequest;
import com.ecomm_app.dto.UserResponse;
import com.ecomm_app.model.Users;
import com.ecomm_app.repository.UserRepository;
import com.ecomm_app.security.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private JwtHelper jwtHelper;

    public ResponseEntity<String> signUp(Users user)
    {
            Optional<Users> exist = userRepository.findByEmail(user.getEmail());
            if (!exist.isPresent()) {
                String encodedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);
                userRepository.save(user);
                return ResponseEntity.ok("User registration successful");
            }

        else
            {
                return ResponseEntity.badRequest().body("User registration failed :email already exist");

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

    public ResponseEntity<UserResponse> validateToken(String token)
    {
        String username=jwtHelper.getUsernameFromToken(token);
        if (username!=null )
        {
            Users user=userRepository.findByEmail(username).orElse(null);
            UserResponse userResponse=new UserResponse(user.getUser_id(),user.getName(),user.getEmail());
            return  new ResponseEntity<UserResponse>(userResponse,HttpStatus.OK);
        }
        else
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }

    }
}
