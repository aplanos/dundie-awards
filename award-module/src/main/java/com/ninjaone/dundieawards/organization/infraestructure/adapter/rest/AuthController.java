package com.ninjaone.dundieawards.organization.infraestructure.adapter.rest;

import com.ninjaone.dundieawards.organization.application.dto.LoginRequestModel;
import com.ninjaone.dundieawards.organization.application.dto.LoginResponseModel;
import com.ninjaone.dundieawards.organization.application.service.InternalUserService;
import com.ninjaone.dundieawards.organization.infraestructure.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@CrossOrigin
@RequestMapping("/api-v1/auth")
public class AuthController {

    private final InternalUserService internalUserService;
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(InternalUserService internalUserService,
                          AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider) {
        this.internalUserService = internalUserService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponseModel login(@RequestBody LoginRequestModel model) {

        final var user = internalUserService.findByUsername(model.getUsername())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "User login is invalid or disabled", null));

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        model.getUsername(),
                        model.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.createToken(authentication);

        final var response = new LoginResponseModel();
        response.setUsername(user.getEmail());
        response.setToken(jwt);
        response.setPermissions(
                internalUserService.getProfileFeatures(user)
        );

        response.setToken(jwt);

        return response;
    }
}
