package com.forexconverter.service;

import com.forexconverter.dto.CurrencyConverterDto;

import java.math.BigDecimal;

public interface CurrencyConverterService {

  CurrencyConverterDto convert(String from, String to, BigDecimal amount);

}
