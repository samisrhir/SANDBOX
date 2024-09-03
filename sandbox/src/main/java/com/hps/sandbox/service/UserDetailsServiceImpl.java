package com.hps.sandbox.service;

import com.hps.sandbox.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userEmailOrUsername) throws UsernameNotFoundException {
        return repository.findByEmailOrUsername(userEmailOrUsername,userEmailOrUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
