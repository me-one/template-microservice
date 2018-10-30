package com.megvii.insight.service.analytics;

import feign.RequestInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.reactive.function.client.WebClient;
import zipkin2.Span;
import zipkin2.reporter.Reporter;

@SpringBootApplication
@EnableEurekaClient
@EnableResourceServer
@EnableOAuth2Client
@EnableCircuitBreaker
@EnableFeignClients
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RefreshScope
public class ServiceAnalyticsApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServiceAnalyticsApplication.class, args);
  }

  /**
   * Use this for debugging (or if there is no Zipkin server running on port 9411)
   */
  @Bean
  @ConditionalOnProperty(value = "sample.zipkin.enabled", havingValue = "false")
  public Reporter<Span> spanReporter() {
    return Reporter.CONSOLE;
  }

  @Bean
  @ConfigurationProperties(prefix = "security.oauth2.client")
  public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
    return new ClientCredentialsResourceDetails();
  }

  @Bean
  public RequestInterceptor oauth2FeignRequestInterceptor() {
    return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), clientCredentialsResourceDetails());
  }

  @Bean
  public OAuth2RestTemplate clientCredentialsRestTemplate() {
    return new OAuth2RestTemplate(clientCredentialsResourceDetails());
  }

  @Bean
  @LoadBalanced
  public WebClient.Builder loadBalancedWebClientBuilder() {
    return WebClient.builder();
  }
}
