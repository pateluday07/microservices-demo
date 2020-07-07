package com.forexconverter.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CurrencyConverterDto {

  private Long id;

  private String from;

  private String to;

  private BigDecimal rate;

  private BigDecimal amount;

  private BigDecimal convertedAmount;

  private Integer port;

  private String message;
}
