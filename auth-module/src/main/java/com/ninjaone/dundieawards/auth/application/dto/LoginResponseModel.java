package com.ninjaone.dundieawards.auth.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter @Setter
public class LoginResponseModel {

    @Schema(description = "Username of the authenticated user", example = "johndoe")
    private String username;

    @Schema(description = "First name of the authenticated user", example = "John")
    private String firstname;

    @Schema(description = "Last name of the authenticated user", example = "Doe")
    private String lastname;

    @Schema(description = "JWT token for authentication", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @Schema(description = "Roles assigned to the user", example = "[\"ROLE_ADMIN\", \"ROLE_USER\"]")
    private Set<String> roles = new HashSet<>();

    @Schema(description = "Permissions assigned to the user", example = "[\"employee_manage\", \"view_reports\"]")
    private Set<String> permissions = new HashSet<>();

    public LoginResponseModel() {
        this.roles = new HashSet<>();
        this.permissions = new HashSet<>();
    }

    public void setPermissions(List<String> permissions) {
        if (permissions != null) {
            this.permissions.addAll(permissions);
        }
    }
}
