package com.cashout.demo.config;

import com.cashout.demo.service.IUserRestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class Config {

    @Bean
    public WebClient webClient(WebClient.Builder builder){
        return builder.baseUrl("http://localhost:8091")
                .build();
    }

//    @Bean
//    public IUserRestClient userRestClient(WebClient client){
//        HttpServiceProxyFactory factory = HttpServiceProxyFactory
//                .builderFor(WebClientAdapter.create(client)).build();
//        return factory.createClient(IUserRestClient.class);
//    }
}
