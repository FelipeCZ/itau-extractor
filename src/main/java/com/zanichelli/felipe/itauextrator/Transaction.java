package com.zanichelli.felipe.itauextrator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue
    private BigInteger id;
    private LocalDate date;
    private String softDescriptor;
    private BigDecimal amount;

    @ManyToOne
    private Category category;
}
