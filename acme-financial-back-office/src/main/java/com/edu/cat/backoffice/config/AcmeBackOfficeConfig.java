package com.edu.cat.backoffice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class AcmeBackOfficeConfig {

    @Bean
    RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(2000);
        clientHttpRequestFactory.setReadTimeout(3000);
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override public boolean hasError(ClientHttpResponse response)
                    throws IOException {
                try {
                    return super.hasError(response);
                } catch (Exception e) {
                    return true;
                }
            }

            @Override public void handleError(ClientHttpResponse response)
                    throws IOException {
                try {
                    super.handleError(response);
                } catch (Exception e) {
                    throw e;
                }
            }
        });
        return restTemplate;
    }
}
