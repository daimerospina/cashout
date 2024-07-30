package com.cashout.demo.controller;

import com.cashout.demo.domain.User;
import com.cashout.demo.repository.UserRepository;
import com.cashout.demo.service.IUserRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private IUserRestClient userRestClient;

    @PostMapping
    public Mono<User> createUser1(@RequestBody User user){
        return userRepository.save(user);
    }

    @GetMapping("/{id}")
    public Mono<User> getUser(@PathVariable String id){
        return userRepository.findById(id);
    }

//    @PostMapping("/self")
//    public Mono<User> createUser(@RequestBody User user){
//        return userRestClient.createUser(user);
//    }
}
