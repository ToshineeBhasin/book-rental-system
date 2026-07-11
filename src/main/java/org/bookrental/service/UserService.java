package org.bookrental.service;

import org.bookrental.dto.common.ApiResponse;
import org.bookrental.dto.request.UserRequest;
import org.bookrental.dto.response.UserResponse;
import org.springframework.stereotype.Service;


public interface UserService {

    ApiResponse<UserResponse> registerUser(UserRequest userRequest );
}
