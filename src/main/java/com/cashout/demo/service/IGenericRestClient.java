package com.cashout.demo.service;

import com.cashout.demo.domain.cashout.Cashout;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IGenericRestClient {
    @PostExchange("/external/doPayment/{userId}/amount/{amount}")
    Mono<String> doPayment(@PathVariable("userId") String userId, @PathVariable("amount") long amount);

    @GetExchange("/external/transaction-history/user/{userId}")
    Flux<Cashout> getTransactionHistory(@PathVariable("userId") String userId);
}
