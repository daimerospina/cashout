package com.cashout.demo.controller;

import com.cashout.demo.domain.cashout.Cashout;
import com.cashout.demo.service.CashoutService;
import com.cashout.demo.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/external")
public class ExternalRestController {

    @Autowired
    CashoutService cashoutService;

    private static final Object APPROVED_PAYMENT = "Approved" ;

    @PostMapping("/doPayment/{userId}/amount/{amount}")
    public Mono<ResponseEntity<String>> doPayment(@PathVariable("userId") String userId, @PathVariable("amount") long amount){
        return Mono.just(Utils.randomPaymentResponse())
                .filter(APPROVED_PAYMENT::equals)
                .map(response -> ResponseEntity.status(HttpStatus.OK).body(Utils.randomPaymentResponse()))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment service failed")));
    }

    @GetMapping("/transaction-history/user/{userId}")
    public Flux<Cashout> getTransactionHistory(@PathVariable("userId") String userId){
        return cashoutService.getCashoutByUser(userId);
    }
}
