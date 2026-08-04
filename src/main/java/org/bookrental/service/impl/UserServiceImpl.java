package org.bookrental.service.impl;

import org.bookrental.common.enums.ResponseStatus;
import org.bookrental.dto.common.ApiResponse;
import org.bookrental.dto.request.UserRequest;
import org.bookrental.dto.response.UserResponse;
import org.bookrental.entity.User;
import org.bookrental.exception.UserAlreadyExistsException;
import org.bookrental.exception.UserNotFoundException;
import org.bookrental.repository.UserRepository;
import org.bookrental.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public ApiResponse<UserResponse> getUserById(Long id){

        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found with id : " + id));

        UserResponse userResponse = new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());

        return new ApiResponse<>(
                ResponseStatus.SUCCESS,
                "User fetched successfully",
                userResponse
        );
    }

    @Override
    public ApiResponse<List<UserResponse>> getAllUsers() {

        List<User> users = userRepository.findAll();

        List<UserResponse> userResponses = new ArrayList<>();

        for (User user : users) {

            UserResponse userResponse = new UserResponse(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getRole()
            );

            userResponses.add(userResponse);
        }

        return new ApiResponse<>(
                ResponseStatus.SUCCESS,
                "Users fetched successfully",
                userResponses
        );
    }

    @Override
    public ApiResponse<UserResponse> updateUser(Long id, UserRequest userRequest) {

        User existingUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        boolean emailChanged = !existingUser.getEmail().equalsIgnoreCase(userRequest.getEmail());

        if (emailChanged && userRepository.existsByEmail(userRequest.getEmail())) {

            throw new UserAlreadyExistsException(
                    "User already exists with email: " +
                            userRequest.getEmail()
            );
        }

        existingUser.setName(userRequest.getName());
        existingUser.setEmail(userRequest.getEmail());
        existingUser.setPassword(userRequest.getPassword());
        existingUser.setRole(userRequest.getRole());

        User updatedUser = userRepository.save(existingUser);

        UserResponse userResponse = new UserResponse(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getEmail(),
                updatedUser.getRole()
        );

        return new ApiResponse<>(
                ResponseStatus.SUCCESS,
                "User updated successfully",
                userResponse
        );
    }

    @Override
    public ApiResponse<Void> deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " + id
                        )
                );

        userRepository.delete(user);

        return new ApiResponse<>(
                ResponseStatus.SUCCESS,
                "User deleted successfully",
                null
        );
    }
}
