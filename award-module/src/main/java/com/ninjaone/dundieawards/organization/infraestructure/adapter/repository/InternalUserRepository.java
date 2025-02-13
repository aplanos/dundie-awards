package com.ninjaone.dundieawards.organization.infraestructure.adapter.repository;

import com.ninjaone.dundieawards.organization.domain.entity.InternalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InternalUserRepository extends JpaRepository<InternalUser, Long> {
    Optional<InternalUser> findUserByUsername(String email);
}
