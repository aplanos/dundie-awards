package com.ninjaone.dundieawards.organization.application.service;


import com.ninjaone.dundieawards.organization.domain.entity.InternalUser;
import com.ninjaone.dundieawards.organization.infraestructure.adapter.repository.InternalUserRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class InternalUserService {

    private final InternalUserRepository userRepository;

    public InternalUserService(InternalUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<InternalUser> findById(long id) {
        return userRepository.findById(id);
    }

    public Optional<InternalUser> findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public List<String> getProfileFeatures(InternalUser user) {
        return Arrays.asList("employee_manage", "organization_manage");
    }
}
