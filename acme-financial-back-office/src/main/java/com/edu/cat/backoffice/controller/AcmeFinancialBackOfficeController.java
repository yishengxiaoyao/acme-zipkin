package com.edu.cat.backoffice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;


@RestController
public class AcmeFinancialBackOfficeController {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired RestTemplate restTemplate;
    @Value("${account.service.address:localhost:8083}") String accountServiceAddress;
    @Value("${customer.service.address:localhost:8084}") String customerServiceAddress;
    private static final int MOCK_PORT = 8765;

    @RequestMapping("/start")
    public String service2MethodInController() throws InterruptedException {
        Thread.sleep(200);
        log.info("Hello from Acme Financial's Backend service. Calling Acme Financial's Account Microservice and then Customer Microservice");
        String service3 = restTemplate.getForObject("http://" + accountServiceAddress + "/start", String.class);
        log.info("Got response from Acme Financial's Account Service [{}]", service3);
        String service4 = restTemplate.getForObject("http://" + customerServiceAddress + "/start", String.class);
        log.info("Got response from Acme Financial's Customer Service [{}]", service4);
        return String.format("Hello from Acme Financial's Backend service. Calling Acme Financial's Account Service [%s] and then Customer Service [%s]", service3, service4);
    }

    @RequestMapping("/readtimeout")
    public String connectionTimeout() throws InterruptedException {
        Thread.sleep(500);
        try {
            log.info("Calling a missing service");
            restTemplate.getForObject("http://localhost:" + MOCK_PORT + "/readtimeout", String.class);
            return "Should blow up";
        } finally {

        }
    }

}
