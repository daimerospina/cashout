package com.cashout.demo.service;

import com.cashout.demo.domain.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface IUserRestClient {
//    @GetExchange("/user/{userId}")
//    Mono<User> getUser(@PathVariable("userId") String userId);
//
//    @PostExchange("/user")
//    Mono<User> createUser(@RequestBody User user);
}
