package com.megvii.insight.service.data.service;

import com.megvii.insight.service.data.domain.dto.StockDetail;
import com.megvii.insight.service.data.domain.model.Stock;
import com.megvii.insight.service.data.domain.model.StockEvent;
import com.megvii.insight.service.data.mapper.StockDetailMapper;
import com.megvii.insight.service.data.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;
import java.util.Random;

@Service
public class StockResource {

  private final StockRepository stockRepository;
  @Autowired
  private StockDetailMapper stockDetailMapper;

  StockResource(StockRepository stockRepository) {
    this.stockRepository = stockRepository;
  }

  public Flux<StockEvent> events(String stockId) {
    Random random = new Random();
    double current = random.doubles(0, (99999 + 1)).findFirst().getAsDouble();
    return Flux.<StockEvent>generate(sink -> sink.next(new StockEvent(stockId, new Date(), current)))
        .delayElements(Duration.ofSeconds(1));
  }

  public Mono<StockDetail> findById(String id) {
    return this.stockRepository.findById(id)
        .map(stock -> stockDetailMapper.mapToStockDetail(stock));
  }

  public Flux<StockDetail> findByName(String name) {
    return this.stockRepository.findByStockName(name)
        .map(stock -> stockDetailMapper.mapToStockDetail(stock));
  }

  public Flux<StockDetail> all() {
    Flux<Stock> stocks = this.stockRepository.findAll();
    return stocks.map(stockDetailMapper::mapToStockDetail);
  }

  public Mono<StockDetail> add(StockDetail stockDetail) {
    Stock stock = stockDetailMapper.mapToStock(stockDetail);
    return this.stockRepository.insert(stock)
        .map(stk -> stockDetailMapper.mapToStockDetail(stk));
  }
}