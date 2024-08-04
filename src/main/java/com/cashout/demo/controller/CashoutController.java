package com.cashout.demo.controller;

import com.cashout.demo.domain.BalanceRequest;
import com.cashout.demo.domain.entities.Cashout;
import com.cashout.demo.domain.cashout.CashoutRequest;
import com.cashout.demo.domain.exception.InsufficientBalanceException;
import com.cashout.demo.domain.exception.PaymentRejectedException;
import com.cashout.demo.domain.exception.UserNotFoundException;
import com.cashout.demo.service.IGenericRestClient;
import com.cashout.demo.service.interfaces.ICashoutService;
import com.cashout.demo.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@RestController
@RequestMapping("/cashout")
public class CashoutController {

    @Autowired
    ICashoutService cashoutService;

    @Autowired
    IUserService userService;

    @Autowired
    IGenericRestClient genericRestClient;

    private static final String INSUFFICIENT_BALANCE = "Saldo insuficiente para realizar el pago" ;
    private static final String PAYMENT_DECLINED = "El pago fue rechazado" ;
    private static final String USER_NOT_FOUND = "Usuario no encontrado" ;
    private static final String RETRYING = "Reintentando pago..." ;
    private static final String APPROVED_PAYMENT = "Approved" ;

    @PostMapping
    public Mono<Cashout> createCashout(@RequestBody CashoutRequest cashoutRequest) {
        return userService.getUserById(cashoutRequest.getUserId())
                .flatMap(user -> userService.validBalance(user, cashoutRequest))
                .filter(isBalanceValid -> isBalanceValid)
                .switchIfEmpty(Mono.error(new InsufficientBalanceException(INSUFFICIENT_BALANCE)))
                .flatMap(isValid -> genericRestClient.doPayment(cashoutRequest.getUserId(), cashoutRequest.getAmount())
                        .retryWhen(Retry.backoff(2, Duration.ofSeconds(1))
                                        .doBeforeRetry(retrySignal -> System.out.println(RETRYING))))
                .filter(APPROVED_PAYMENT::equals)
                .doOnError(Mono::error)
                .switchIfEmpty(Mono.error(new PaymentRejectedException(PAYMENT_DECLINED)))
                .flatMap(cashout -> userService.updateBalance(cashoutRequest.getUserId(),
                                new BalanceRequest(-cashoutRequest.getAmount()))
                .flatMap(voucher -> cashoutService.createCashout(cashoutRequest)));
    }

    @GetMapping("/user/{userId}")
    public Flux<Cashout> getCashoutByUser(@PathVariable("userId") String userId){
        return genericRestClient.getTransactionHistory(userId)
                .switchIfEmpty(Mono.error(new UserNotFoundException(USER_NOT_FOUND)));
    }

}
