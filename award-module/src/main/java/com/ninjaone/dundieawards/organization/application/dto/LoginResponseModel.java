package com.ninjaone.dundieawards.organization.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter @Setter
public class LoginResponseModel {

    String username;
    String firstname;
    String lastname;
    String token;

    Set<String> roles;
    Set<String> permissions;

    public LoginResponseModel() {
        this.roles = new HashSet<>();
        this.permissions = new HashSet<>();
    }

    public void setPermissions(List<String> permissions) {
        if(permissions != null) {
            this.permissions.addAll(permissions);
        }
    }

}
