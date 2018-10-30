package com.megvii.insight.service.analytics.client;

import com.megvii.insight.service.analytics.client.fallback.FinanceServiceClientFallback;
import com.megvii.insight.service.analytics.domain.dto.StockDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(value = "service-finance", fallback = FinanceServiceClientFallback.class)
public interface FinanceServiceClient {

  @RequestMapping(value = "/rest/stock/symbol/{symbol}/interval/{interval}",
      method = RequestMethod.GET,
      produces = {
          MediaType.APPLICATION_JSON_VALUE
      })
  StockDetailDTO getFinanceService(@PathVariable("symbol") String symbol,
                                   @PathVariable("interval") String interval);

  @RequestMapping("/rest/stock/greeting")
  String greeting();
}