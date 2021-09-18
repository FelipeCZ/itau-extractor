package com.zanichelli.felipe.itauextractor;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, BigInteger> {

    List<Transaction> findAllByDateBetweenOrderByDate(final LocalDate begin, final LocalDate end);
    void deleteAllByDateBetweenOrderByDate(final LocalDate begin, final LocalDate end);

}
