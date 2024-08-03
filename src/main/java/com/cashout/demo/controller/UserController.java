package com.cashout.demo.controller;

import com.cashout.demo.domain.BalanceRequest;
import com.cashout.demo.domain.User;
import com.cashout.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> createUser1(@RequestBody User user){
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public Mono<User> getUser(@PathVariable String id){
        return userService.getUserById(id);
    }

    @PutMapping("/{id}/balance")
    public Mono<User> updateBalance(@PathVariable String id, @RequestBody BalanceRequest newBalance){
        return userService.updateBalance(id, newBalance);
    }
}
