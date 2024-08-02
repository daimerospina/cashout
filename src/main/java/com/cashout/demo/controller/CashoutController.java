package com.cashout.demo.controller;

import com.cashout.demo.domain.cashout.Cashout;
import com.cashout.demo.domain.cashout.CashoutRequest;
import com.cashout.demo.domain.exception.BalanceException;
import com.cashout.demo.domain.exception.PaymentRejectedException;
import com.cashout.demo.service.CashoutService;
import com.cashout.demo.service.IGenericRestClient;
import com.cashout.demo.service.UserService;
import com.cashout.demo.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cashout")
public class CashoutController {

    @Autowired
    CashoutService cashoutService;

    @Autowired
    UserService userService;

    @Autowired
    IGenericRestClient genericRestClient;

    @PostMapping
    public Mono<Cashout> createCashout(@RequestBody CashoutRequest cashoutRequest) {
        return userService.getUserById(cashoutRequest.getUserId())
                .flatMap(user -> userService.validBalance(user, cashoutRequest)
                        .flatMap(validBalance -> {
                            if (validBalance) {
                                return genericRestClient.doPayment(cashoutRequest.getUserId(), cashoutRequest.getAmount())
                                        .flatMap(paymentResult -> {
                                            if (paymentResult.equals("Approved")) {
                                                return cashoutService.createCashout(cashoutRequest);
                                            } else {
                                                return Mono.error(new PaymentRejectedException("Error en el pago: " + paymentResult));
                                            }
                                        });
                            } else {
                                return Mono.error(new BalanceException("Saldo insuficiente"));
                            }
                        })
                );
    }

    @PostMapping("/doPayment/{userId}/amount/{amount}")
    public Mono<String> doPayment(@PathVariable("userId") String userId, @PathVariable("amount") long amount){
        return Mono.just(Utils.randomPaymentResponse());
    }

    @PostMapping("/tmp")
    public Mono<String> doPayment2(){
        return genericRestClient.doPayment("1", 1l);
    }
}
