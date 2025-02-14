package com.ninjaone.dundieawards.organization.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequestModel {
    String username;
    String password;
}
