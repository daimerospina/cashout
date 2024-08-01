package com.cashout.demo.controller;

import com.cashout.demo.domain.cashout.Cashout;
import com.cashout.demo.domain.cashout.CashoutRequest;
import com.cashout.demo.repository.CashoutRepository;
import com.cashout.demo.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cashout")
public class CashoutController {

    @Autowired
    CashoutRepository cashoutRepository;

    @PostMapping
    public Mono<Cashout> doCashout(@RequestBody CashoutRequest cashoutRequest){
        return cashoutRepository.save(new Cashout(
                Utils.generateRandomValue(), cashoutRequest.getUserId(), cashoutRequest.getAmount()
        ));
    }
}
