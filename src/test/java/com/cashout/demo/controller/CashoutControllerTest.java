package com.cashout.demo.controller;

import com.cashout.demo.domain.entities.Cashout;
import com.cashout.demo.domain.exception.GlobalExceptionHandle;
import com.cashout.demo.service.IGenericRestClient;
import com.cashout.demo.service.interfaces.ICashoutService;
import com.cashout.demo.service.interfaces.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {GlobalExceptionHandle.class, CashoutController.class})
@WebFluxTest(CashoutController.class)
public class CashoutControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    ICashoutService cashoutService;

    @MockBean
    IUserService userService;

    @MockBean
    IGenericRestClient genericRestClient;

    @Test
    void getCashoutByUser(){

        var cashout1 = new Cashout("1", "1", 10);
        var cashout2 = new Cashout("2", "1", 20);

        when(cashoutService.getCashoutsByUser("1")).thenReturn(Flux.just(cashout1, cashout2));

        webTestClient.get()
                .uri("/cashout/user/{id}", "1")
                .exchange()
                .expectStatus().isOk();

    }
}
