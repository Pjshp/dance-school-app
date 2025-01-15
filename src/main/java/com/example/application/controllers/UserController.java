package com.example.application.controllers;


import com.example.application.data.User;
import com.example.application.data.UserRepository;
import com.example.application.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/email")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        Optional<User> user = userService.findUserByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @GetMapping("/users-summary")
//    public ResponseEntity<List<Object[]>> getOrdersSummary() {
//        return ResponseEntity.ok(userRepository.findOrdersNamesPhonesAndTotalPayment());
//    }
//
//    @GetMapping("/users-phone-summary")
//    public ResponseEntity<List<Object[]>> getOrdersByPhoneSummary() {
//        return ResponseEntity.ok(userRepository.findOrdersSummaryByPhone());
//    }
}
