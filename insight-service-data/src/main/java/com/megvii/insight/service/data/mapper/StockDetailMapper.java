package com.megvii.insight.service.data.mapper;

import com.megvii.insight.service.data.domain.dto.StockDetail;
import com.megvii.insight.service.data.domain.model.Stock;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StockDetailMapper {

  StockDetail mapToStockDetail(Stock stock);

  @InheritInverseConfiguration
  Stock mapToStock(StockDetail userBean);
}