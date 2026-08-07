package org.bookrental.dto.request;

import org.bookrental.common.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class UserRequest {

    @NotNull(message = "Name should not be blank.")
    private String name;

    @Email(message = "Email must be unique.")
    @NotNull(message = "Email should not be blank.")
    private String email;

    @NotNull(message = "Password should not be blank.")
    private String password;

    @NotNull(message = "Role should not be blank.")
    private Role role;

    public UserRequest(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }



}
