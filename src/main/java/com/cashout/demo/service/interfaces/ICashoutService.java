package com.cashout.demo.service.interfaces;

import com.cashout.demo.domain.cashout.CashoutRequest;
import com.cashout.demo.domain.entities.Cashout;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICashoutService {
    Mono<Cashout> createCashout(CashoutRequest cashoutRequest);
    Flux<Cashout> getCashoutsByUser(String userId);
}
