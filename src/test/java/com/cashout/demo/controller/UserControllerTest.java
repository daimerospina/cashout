package com.cashout.demo.controller;

import com.cashout.demo.domain.BalanceRequest;
import com.cashout.demo.domain.entities.User;
import com.cashout.demo.domain.exception.GlobalExceptionHandle;
import com.cashout.demo.domain.exception.UserNotFoundException;
import com.cashout.demo.service.interfaces.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {GlobalExceptionHandle.class, UserController.class})
@WebFluxTest(UserController.class)
public class UserControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    IUserService userService;

    @Test
    void createUser(){
        var user = new User("1", "Daimer", 10);

        when(userService.createUser(user)).thenReturn(Mono.just(user));

        webTestClient.post()
                .uri("/users")
                .bodyValue(user)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Daimer")
                .jsonPath("$.balance").isEqualTo(10);
    }

    @Test
    void getUser(){
        var user = new User("1", "Daimer", 10);

        when(userService.getUserById("1")).thenReturn(Mono.just(user));

        webTestClient.get()
                .uri("/users/{id}", "1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Daimer")
                .jsonPath("$.balance").isEqualTo(10);
    }

    @Test
    void updateBalance(){
        var user = new User("1", "Daimer", 20);
        var balanceRequest = new BalanceRequest(10);

        when(userService.updateBalance("1", balanceRequest)).thenReturn(Mono.just(user));

        webTestClient.put()
                .uri("/users/{id}/balance", "1")
                .bodyValue(balanceRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Daimer")
                .jsonPath("$.balance").isEqualTo(20);
    }

    @Test
    void updateBalanceUserNotFound(){
        var balanceRequest = new BalanceRequest(10);

        when(userService.updateBalance("1", balanceRequest))
                .thenReturn(Mono.error(new UserNotFoundException("Cliente no encontrado")));

        webTestClient.put()
                .uri("/users/{id}/balance", "1")
                .bodyValue(balanceRequest)
                .exchange()
                .expectStatus().isNotFound();
    }
}
