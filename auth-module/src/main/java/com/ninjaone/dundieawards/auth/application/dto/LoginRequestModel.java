package com.ninjaone.dundieawards.auth.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequestModel {
    @Schema(description = "Username for authentication", example = "johndoe")
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Schema(description = "Password for authentication", example = "P@ssw0rd!")
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
