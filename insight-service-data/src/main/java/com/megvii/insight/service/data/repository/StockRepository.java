package com.megvii.insight.service.data.repository;

import com.megvii.insight.service.data.domain.model.Stock;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface StockRepository extends ReactiveMongoRepository<Stock, String> {

  Flux<Stock> findByStockName(final String stockName);
}