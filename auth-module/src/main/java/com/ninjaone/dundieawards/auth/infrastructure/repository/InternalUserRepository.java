package com.ninjaone.dundieawards.auth.infrastructure.repository;

import com.ninjaone.dundieawards.auth.domain.entity.InternalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InternalUserRepository extends JpaRepository<InternalUser, Long> {
    Optional<InternalUser> findUserByUsername(String email);
}
