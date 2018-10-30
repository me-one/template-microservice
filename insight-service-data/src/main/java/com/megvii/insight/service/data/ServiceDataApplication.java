package com.megvii.insight.service.data;

import com.megvii.insight.service.data.domain.dto.StockDetail;
import com.megvii.insight.service.data.service.StockResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
@EnableEurekaClient
@EnableJpaRepositories(basePackages = "com.megvii.insight.service.data.repository")
public class ServiceDataApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServiceDataApplication.class, args);
  }

  @Bean
  RouterFunction<?> routes(StockResource stockResource) {

    return nest(path("/stocks"),
        route(GET("/name/{name}"),
            request -> ok().body(stockResource.findByName(request.pathVariable("name")), StockDetail.class))
            .andRoute(GET("/all"),
                request -> ok().body(stockResource.all(), StockDetail.class))
    );
  }

}
