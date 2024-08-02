package com.cashout.demo.service;

import com.cashout.demo.domain.cashout.AmountRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface IGenericRestClient {
    @PostExchange("/cashout/doPayment/{userId}/amount/{amount}")
    Mono<String> doPayment(@PathVariable("userId") String userId, @PathVariable("amount") long amount);
}
