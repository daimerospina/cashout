package com.cashout.demo.service;

import com.cashout.demo.domain.BalanceRequest;
import com.cashout.demo.domain.User;
import com.cashout.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Mono<User> getUserById (String userId){
        return userRepository.findById(userId);
    }

    public Mono<User> createUser (User user){
        return userRepository.save(user);
    }

    public Mono<User> updateBalance (String userId, BalanceRequest newBalance){
        return userRepository.findById(userId)
                .flatMap(user -> {
                    user.setBalance(user.getBalance() + newBalance.getBalance());
                    return userRepository.save(user);
                });
    }
}