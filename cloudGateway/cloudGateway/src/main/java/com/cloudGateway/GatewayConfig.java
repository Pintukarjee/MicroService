package com.cloudGateway;

import org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class GatewayConfig {

    @Bean
    public RouterFunction<ServerResponse> orderServiceRoute() {
        return GatewayRouterFunctions.route("order-service")
                .route(RequestPredicates.path("/v1/order/**"),
                        HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("ORDER-SERVICE"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> paymentServiceRoute() {
        return GatewayRouterFunctions.route("payment-service")
                .route(RequestPredicates.path("/v1/payment/**"),
                        HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("PAYMENT-SERVICE"))
                .build();
    }
}