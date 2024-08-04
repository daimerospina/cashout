package com.cashout.demo.service;

import com.cashout.demo.domain.BalanceRequest;
import com.cashout.demo.domain.entities.User;
import com.cashout.demo.domain.cashout.CashoutRequest;
import com.cashout.demo.domain.exception.UserNotFoundException;
import com.cashout.demo.repository.UserRepository;
import com.cashout.demo.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    private static final String USER_NOT_FOUNT = "Cliente no encontrado";

    @Override
    public Mono<User> getUserById (String userId){
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new UserNotFoundException(USER_NOT_FOUNT)));
    }

    @Override
    public Mono<User> createUser (User user){
        return userRepository.save(user);
    }

    @Override
    public Mono<User> updateBalance (String userId, BalanceRequest newBalance){
        return userRepository.findById(userId)
                .flatMap(user -> {
                    user.setBalance(user.getBalance() + newBalance.getBalance());
                    return userRepository.save(user);
                })
                .switchIfEmpty(Mono.error(new UserNotFoundException(USER_NOT_FOUNT)));
    }

    @Override
    public Mono<Boolean> validBalance (User user, CashoutRequest cashoutRequest){
        return Mono.fromCallable(() -> user.getBalance() >= cashoutRequest.getAmount());
    }
}
