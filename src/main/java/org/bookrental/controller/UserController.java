package org.bookrental.controller;

import org.bookrental.dto.common.ApiResponse;
import org.bookrental.dto.request.UserRequest;
import org.bookrental.dto.response.UserResponse;
import org.bookrental.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> registerUser(@Valid @RequestBody UserRequest userRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(userRequest));
    }
}
