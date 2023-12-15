package com.ecomm_app.controller;


import com.ecomm_app.dto.UserResponse;
import com.ecomm_app.model.Users;
import com.ecomm_app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
public class UserControllerTest
{
    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;
    UserResponse userResponse;

    @Test
    public void testsignUp()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//        userResponse=new UserResponse("Success","User registration successful");
        Users usertoadd = new Users(1, "Lokesh","lokesh@gmail.com",897897885,"lokesh@123");
        ResponseEntity<UserResponse> responseEntity=new ResponseEntity<>(userResponse, HttpStatus.OK);

        when(userService.signUp(usertoadd)).thenReturn(responseEntity);

        ResponseEntity<UserResponse> response = userController.signUp(usertoadd);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getBody()).isEqualTo(userResponse);

    }

}
