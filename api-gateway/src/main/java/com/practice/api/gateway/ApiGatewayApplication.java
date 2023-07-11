package com.practice.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
//@zipkin2.server.internal.EnableZipkinServer
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	/*
	 * @Bean RouteLocator gateway(RouteLocatorBuilder rlb) { return
	 * rlb.routes().route(route ->
	 * route.path("/EMPLOYEE-SERVICE").uri("lb://EMPLOYEE-SERVICE/")).build(); }
	 */

}
