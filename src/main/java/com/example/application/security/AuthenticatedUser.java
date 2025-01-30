package com.example.application.security;

import com.example.application.data.User;
import com.example.application.data.UserRepository;
import com.vaadin.flow.spring.security.AuthenticationContext;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AuthenticatedUser {

    private final UserRepository userRepository;
    private final AuthenticationContext authenticationContext;

    public AuthenticatedUser(AuthenticationContext authenticationContext, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.authenticationContext = authenticationContext;
    }

//    @Transactional
//    public Optional<User> get() {
//        return authenticationContext.getAuthenticatedUser(UserDetails.class)
//                .flatMap(userDetails -> userRepository.findByEmail(userDetails.getUsername())); // Zmieniono na findByEmail
//    }
    @Transactional
    public Optional<User> get() {
        Optional<UserDetails> userDetailsOptional = authenticationContext.getAuthenticatedUser(UserDetails.class);
        if (userDetailsOptional.isPresent()) {
            UserDetails userDetails = userDetailsOptional.get();
            System.out.println("Zalogowany użytkownik: " + userDetails.getUsername());
            return userRepository.findByEmail(userDetails.getUsername());
        } else {
            System.out.println("Brak zalogowanego użytkownika w AuthenticationContext");
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<Long> getAuthenticatedUserId() {
        return get().map(User::getUserId);
    }

    public void logout() {
        authenticationContext.logout();
    }


}
