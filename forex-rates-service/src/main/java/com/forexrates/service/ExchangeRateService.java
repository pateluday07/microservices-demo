package com.forexrates.service;

import com.forexrates.entity.ExchangeRate;

public interface ExchangeRateService {

  ExchangeRate getExchangeRate(String from, String to);
}
