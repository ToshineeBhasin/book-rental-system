package org.bookrental.service;

import org.bookrental.dto.request.LoginRequest;
import org.bookrental.dto.response.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest loginRequest);
}
