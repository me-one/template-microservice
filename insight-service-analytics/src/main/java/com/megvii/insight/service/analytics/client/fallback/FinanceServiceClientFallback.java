package com.megvii.insight.service.analytics.client.fallback;

import com.megvii.insight.service.analytics.client.FinanceServiceClient;
import com.megvii.insight.service.analytics.domain.dto.StockDetailDTO;
import org.springframework.stereotype.Component;

@Component
public class FinanceServiceClientFallback implements FinanceServiceClient {

  @Override
  public StockDetailDTO getFinanceService(String symbol, String interval) {
    return new StockDetailDTO();
  }

  @Override
  public String greeting() {
    return "Hystrix Fallback Greeting";
  }
}