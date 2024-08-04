package com.cashout.demo.service;

import com.cashout.demo.domain.entities.Cashout;
import com.cashout.demo.domain.cashout.CashoutRequest;
import com.cashout.demo.repository.CashoutRepository;
import com.cashout.demo.service.interfaces.ICashoutService;
import com.cashout.demo.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CashoutService implements ICashoutService {

    @Autowired
    private CashoutRepository cashoutRepository;

    @Override
    public Mono<Cashout> createCashout (CashoutRequest cashoutRequest){
        return cashoutRepository.save(new Cashout(
                Utils.generateRandomValue(), cashoutRequest.getUserId(), cashoutRequest.getAmount()
        ));
    }

    @Override
    public Flux<Cashout> getCashoutsByUser(String userId){
        return cashoutRepository.findByUserId(userId);
    }

}
