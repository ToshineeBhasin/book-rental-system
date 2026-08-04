package org.bookrental.service;

import org.bookrental.dto.common.ApiResponse;
import org.bookrental.dto.request.UserRequest;
import org.bookrental.dto.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    ApiResponse<UserResponse> registerUser(UserRequest userRequest );
    ApiResponse<UserResponse> getUserById(Long id);
    ApiResponse<List<UserResponse>> getAllUsers();
    ApiResponse<UserResponse> updateUser(Long id, UserRequest userRequest);
    ApiResponse<Void> deleteUser(Long id);
}
