package com.ninjaone.dundieawards.organization.infraestructure.security;

import com.ninjaone.dundieawards.organization.infraestructure.adapter.repository.InternalUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final InternalUserRepository userRepository;

    public CustomUserDetailsService(InternalUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));


        List<GrantedAuthority> authorities = Stream.of("employee_manage", "organization_manage")
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new User(username, user.getPasswordHash(), authorities);
    }

}
