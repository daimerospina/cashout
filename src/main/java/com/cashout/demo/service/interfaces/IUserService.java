package com.cashout.demo.service.interfaces;

import com.cashout.demo.domain.BalanceRequest;
import com.cashout.demo.domain.cashout.CashoutRequest;
import com.cashout.demo.domain.entities.User;
import reactor.core.publisher.Mono;

public interface IUserService {
    Mono<User> createUser(User user);
    Mono<User> getUserById(String userId);
    Mono<Boolean> validBalance(User user, CashoutRequest cashoutRequest);
    Mono<User> updateBalance(String userId, BalanceRequest newBalance);
}
