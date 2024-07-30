package com.cashout.demo.controller;

import com.cashout.demo.domain.User;
import com.cashout.demo.repository.UserRepository;
import com.cashout.demo.service.IUserRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IUserRestClient userRestClient;

    @PostMapping
    public Mono<User> createUser1(@RequestBody User user){
        return userRepository.save(user);
    }

    @PostMapping("/self")
    public Mono<User> createUser(@RequestBody User user){
        return userRestClient.createUser(user);
    }
}
