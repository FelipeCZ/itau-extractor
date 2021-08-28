package com.zanichelli.felipe.itauextrator;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryService categoryService;

    public Transaction build(final String date, final String softDescriptor, final String amount) {
        Transaction t = new Transaction();
        t.setDate(LocalDate.parse(date));
        t.setSoftDescriptor(softDescriptor);
        t.setValue(new BigDecimal(amount));
        return t;
    }

    public Transaction classify(final String[] transactionDetail) {
        Transaction t = build(transactionDetail[0], transactionDetail[1], transactionDetail[2]);
        t.setCategory(categoryService.newClassifier().findCategory(t.getSoftDescriptor()));
        transactionRepository.save(t);
        return t;
    }

}
