package org.bookrental.service.impl;

import org.bookrental.common.enums.ResponseStatus;
import org.bookrental.dto.common.ApiResponse;
import org.bookrental.dto.request.UserRequest;
import org.bookrental.dto.response.UserResponse;
import org.bookrental.entity.User;
import org.bookrental.exception.UserAlreadyExistsException;
import org.bookrental.repository.UserRepository;
import org.bookrental.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public ApiResponse<UserResponse>  registerUser(UserRequest userRequest){
        if(userRepository.existsByEmail(userRequest.getEmail())){
            throw new UserAlreadyExistsException("User already exists with email: " +  userRequest.getEmail());
        }

        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setRole(userRequest.getRole());

        User savedUser = userRepository.save(user);

        UserResponse userResponse = new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
        return new ApiResponse<>(
                ResponseStatus.SUCCESS,"User Register Successfully", userResponse
        );
    }


}
