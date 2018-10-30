package com.megvii.insight.service.analytics.controllor;

import com.google.gson.Gson;
import com.megvii.insight.service.analytics.client.FinanceServiceClient;
import com.megvii.insight.service.analytics.domain.dto.StockDetailDTO;
import com.megvii.insight.service.analytics.domain.model.StockDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.HystrixCommands;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@EnableEurekaClient
@RestController
@RequestMapping("/finance")
public class FinanceController {

  @Autowired
  private FinanceServiceClient financeServiceClient;
  @Autowired
  private WebClient.Builder webClientBuilder;

  @GetMapping("/stock/{symbol}")
  public String getStock(@PathVariable String symbol) {
    StockDetailDTO stockDetailDTO = financeServiceClient.getFinanceService(symbol, "1min");
    Gson gson = new Gson();
    return gson.toJson(stockDetailDTO);
  }

  @GetMapping("/stock/summary/{name}")
  public Flux<StockDetail> getStockSummary(@PathVariable String name) {
    Flux<StockDetail> stockDetailFlux = webClientBuilder.build()
        .get().uri("http://data-service/stocks/name/{name}", name)
        .retrieve().bodyToFlux(StockDetail.class);

    return HystrixCommands.from(stockDetailFlux)
        .fallback(Flux.just(new StockDetail()))
        .commandName("getStockSummary")
        .toFlux();
  }

  @GetMapping("/greeting")
  public String greeting() {
    return financeServiceClient.greeting();
  }
}
