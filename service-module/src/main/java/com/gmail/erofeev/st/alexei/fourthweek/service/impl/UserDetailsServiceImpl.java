package com.gmail.erofeev.st.alexei.fourthweek.service.impl;

import com.gmail.erofeev.st.alexei.fourthweek.service.UserService;
import com.gmail.erofeev.st.alexei.fourthweek.service.model.AppUserPrincipal;
import com.gmail.erofeev.st.alexei.fourthweek.service.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = userService.findUserByName(username);
        if (user == null) {
            String message = "User is not found: " + username;
            logger.error(message);
            throw new UsernameNotFoundException(message);
        }
        return new AppUserPrincipal(user);
    }
}
