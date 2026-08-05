package org.bookrental.controller;

import org.bookrental.common.enums.ResponseStatus;
import org.bookrental.dto.common.ApiResponse;
import org.bookrental.dto.request.LoginRequest;
import org.bookrental.dto.response.LoginResponse;
import org.bookrental.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {

        LoginResponse loginResponse =  authService.login(loginRequest);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        ResponseStatus.SUCCESS,
                        "Login successful",
                        loginResponse
                )
        );
    }
}