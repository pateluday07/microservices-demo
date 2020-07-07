package com.forexrates.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ExchangeRate {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "currency_from", nullable = false)
  private String from;

  @Column(name = "currency_to", nullable = false)
  private String to;

  @Column(nullable = false)
  private BigDecimal rate;

  @Transient
  private Integer port;
}
