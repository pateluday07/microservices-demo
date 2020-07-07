package com.forexconverter.feign;

import com.forexconverter.dto.CurrencyConverterDto;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("forex-rates-service")
@RibbonClient("forex-rates-service")
public interface ExchangeRatesFeignService {

  @GetMapping("/api/exchange-rates/from/{from}/to/{to}")
  CurrencyConverterDto getExchangeRate(@PathVariable("from") String from, @PathVariable("to") String to);

}
