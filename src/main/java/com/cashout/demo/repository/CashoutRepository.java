package com.cashout.demo.repository;

import com.cashout.demo.domain.cashout.Cashout;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashoutRepository extends ReactiveMongoRepository<Cashout, String> {
}
