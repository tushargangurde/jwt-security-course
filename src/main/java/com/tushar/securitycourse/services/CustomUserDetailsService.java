package com.tushar.securitycourse.services;

import com.tushar.securitycourse.model.AppUser;
import com.tushar.securitycourse.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Inside CustomUserDetailsService ---------------> loadUserByUsername");
        AppUser user = userRepository.findByUsername(username);
        if (user == null) {
            log.info("User not available with username:" + username);
            throw new UsernameNotFoundException("User not available with username:" + username);
        } else {
            log.info("User available with username:" + username);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
            return new User(user.getUsername(), user.getPassword(), authorities);
        }
    }
}
