package com.forexconverter.service;

import com.forexconverter.dto.CurrencyConverterDto;
import com.forexconverter.feign.ExchangeRatesFeignService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import feign.FeignException;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Log4j2
public class CurrencyConverterServiceImpl implements CurrencyConverterService {

  @Value("${server.port}")
  private Integer port;

  private final ExchangeRatesFeignService exchangeRatesFeignService;

  @Override
  @HystrixCommand(fallbackMethod = "fallBackConverter")
  public CurrencyConverterDto convert(String from, String to, BigDecimal amount) {
    log.info("Converting {} To {} For The Amount {}", from, to, amount);
    CurrencyConverterDto converterDto = exchangeRatesFeignService.getExchangeRate(from, to);
    log.info("Got Exchange Rate From The Forex Service {}", converterDto);
    converterDto.setAmount(amount);
    converterDto.setPort(port);
    converterDto.setConvertedAmount(amount.multiply(converterDto.getRate()));
    return converterDto;
  }

  private CurrencyConverterDto fallBackConverter(String from, String to, BigDecimal amount, Throwable cause) {
    if (cause.getCause() instanceof FeignException) {
      FeignException feignException = (FeignException) cause.getCause();
      if (HttpStatus.NOT_FOUND.value() == feignException.status()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry The Conversion Rate Is Not Available For "
            .concat(from).concat(" To ").concat(to).concat(" So We Can Not Provide The Converted Amount"));
      }
    }
    CurrencyConverterDto converterDto = new CurrencyConverterDto();
    converterDto.setFrom(from);
    converterDto.setTo(to);
    converterDto.setAmount(amount);
    converterDto.setPort(port);
    converterDto.setMessage("Sorry The Forex Rates Service IS Not Available At The Moment");
    return converterDto;
  }
}
