package com.forexrates.service;

import com.forexrates.entity.ExchangeRate;
import com.forexrates.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Log4j2
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {

  @Value("${server.port}")
  private Integer port;

  private final ExchangeRateRepository exchangeRateRepository;

  @Override
  @Transactional(readOnly = true)
  public ExchangeRate getExchangeRate(String from, String to) {
    log.info("Getting Exchange Rate For {} To {}", from, to);
    ExchangeRate exchangeRate = exchangeRateRepository.findByFromAndTo(from, to)
        .orElseThrow(()->
            new ResponseStatusException(HttpStatus.NOT_FOUND,"Exchange Rate Not Available For "
                .concat(from)
                .concat(" To ")
                .concat(to)));
    exchangeRate.setPort(port);
    return exchangeRate;
  }

}
