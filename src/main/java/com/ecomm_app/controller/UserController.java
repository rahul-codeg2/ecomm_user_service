package com.ecomm_app.controller;

import com.ecomm_app.model.JwtRequest;
import com.ecomm_app.model.JwtResponse;
import com.ecomm_app.model.Users;
import com.ecomm_app.security.JwtHelper;
import com.ecomm_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController
{
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtHelper jwtHelper;


    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody Users user)
    {
        return userService.signUp(user);

    }


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest)
    {

        UserDetails userDetails=userDetailsService.loadUserByUsername(jwtRequest.getEmail());
        userService.authenticate(userDetails,jwtRequest);
        String token=this.jwtHelper.generateToken(userDetails);
        JwtResponse jwtResponse=JwtResponse.builder()
                .jwtToken(token).username(userDetails.getUsername()).build();
        return  new ResponseEntity<>(jwtResponse, HttpStatus.OK);

    }
    @PutMapping("/update_profile")
    public ResponseEntity<Users> updateProfile(@RequestBody Users user)
    {
        Users updated=userService.updateProfile( user);
        return new ResponseEntity<>(updated,HttpStatus.OK);
    }
    @GetMapping("/get_profile")
    public ResponseEntity<Users> getProfile(@RequestParam String email)
    {
        return userService.getProfile( email);

    }
}
