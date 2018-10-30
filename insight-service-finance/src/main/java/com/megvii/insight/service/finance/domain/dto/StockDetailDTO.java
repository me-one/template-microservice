package com.megvii.insight.service.finance.domain.dto;

import com.megvii.insight.service.finance.domain.model.StockPrice;

import java.util.ArrayList;
import java.util.List;

public class StockDetailDTO {

  private List<StockPrice> stockPrices = new ArrayList<>();

  public List<StockPrice> getStockPrices() {
    return stockPrices;
  }

  public void setStockPrices(List<StockPrice> stockPrices) {
    this.stockPrices = stockPrices;
  }

  public void addStockPrice(StockPrice stockPrice) {
    this.stockPrices.add(stockPrice);
  }
}