package org.bookrental.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bookrental.common.enums.ResponseStatus;
import org.bookrental.dto.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle( HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
            throws IOException {

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<Object> apiResponse = new ApiResponse<>(
                ResponseStatus.FAILED,
                "You do not have permission to access this resource",
                null
        );

        objectMapper.writeValue(
                response.getOutputStream(),
                apiResponse
        );
    }
}
