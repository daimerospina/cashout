package com.cashout.demo.service;

import com.cashout.demo.domain.cashout.Cashout;
import com.cashout.demo.domain.cashout.CashoutRequest;
import com.cashout.demo.repository.CashoutRepository;
import com.cashout.demo.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CashoutService {

    @Autowired
    private CashoutRepository cashoutRepository;

    public Mono<Cashout> createCashout (CashoutRequest cashoutRequest){
        return cashoutRepository.save(new Cashout(
                Utils.generateRandomValue(), cashoutRequest.getUserId(), cashoutRequest.getAmount()
        ));
    }

}
