package com.cashout.demo.controller;

import com.cashout.demo.domain.BalanceRequest;
import com.cashout.demo.domain.cashout.Cashout;
import com.cashout.demo.domain.cashout.CashoutRequest;
import com.cashout.demo.domain.exception.BalanceException;
import com.cashout.demo.domain.exception.ClientNotFoundException;
import com.cashout.demo.domain.exception.PaymentRejectedException;
import com.cashout.demo.service.CashoutService;
import com.cashout.demo.service.IGenericRestClient;
import com.cashout.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@RestController
@RequestMapping("/cashout")
public class CashoutController {

    @Autowired
    CashoutService cashoutService;

    @Autowired
    UserService userService;

    @Autowired
    IGenericRestClient genericRestClient;

    private static final Object APPROVED_PAYMENT = "Approved" ;

    @PostMapping
    public Mono<Cashout> createCashout(@RequestBody CashoutRequest cashoutRequest) {
        return userService.getUserById(cashoutRequest.getUserId())
                .flatMap(user -> userService.validBalance(user, cashoutRequest))
                .filter(isBalanceValid -> isBalanceValid)
                .switchIfEmpty(Mono.error(new BalanceException("Saldo insuficiente para realizar el pago")))
                .flatMap(isValid -> genericRestClient.doPayment(cashoutRequest.getUserId(), cashoutRequest.getAmount())
                        .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
                                        .doBeforeRetry(retrySignal -> System.out.println("Reintentando pago..."))))
                .filter(APPROVED_PAYMENT::equals)
                .doOnError(Mono::error)
                .switchIfEmpty(Mono.error(new PaymentRejectedException("El pago fue rechazado")))
                .flatMap(cashout -> userService.updateBalance(cashoutRequest.getUserId(), new BalanceRequest(-cashoutRequest.getAmount()))
                .flatMap(voucher -> cashoutService.createCashout(cashoutRequest)));
    }

    @GetMapping("/user/{userId}")
    public Flux<Cashout> getCashoutByUser(@PathVariable("userId") String userId){
        return genericRestClient.getTransactionHistory(userId);
    }

}
