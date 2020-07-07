package com.forexrates.controller;

import com.forexrates.entity.ExchangeRate;
import com.forexrates.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exchange-rates")
@RequiredArgsConstructor
public class ExchangeRateController {

  private final ExchangeRateService exchangeRateService;

  @GetMapping("/from/{from}/to/{to}")
  public ExchangeRate getExchangeRate(@PathVariable String from, @PathVariable String to) {
    return exchangeRateService.getExchangeRate(from, to);
  }
}
