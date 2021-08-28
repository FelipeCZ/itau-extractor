package com.zanichelli.felipe.itauextrator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        t.setDate(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        t.setSoftDescriptor(softDescriptor);
        t.setAmount(new BigDecimal(amount.replaceAll("\\.", "").replaceAll(",", ".")).setScale(2));
        return t;
    }

    public Transaction classify(final String[] transactionDetail) {
        Transaction t = build(transactionDetail[0], transactionDetail[1], transactionDetail[2]);
        t.setCategory(categoryService.newClassifier().findCategory(t.getSoftDescriptor()));
        transactionRepository.save(t);
        return t;
    }

    public Map<Category, List<Transaction>> getAllMonthlyTransactionsGroupedByCategory(int month, int year) {
        var allT = transactionRepository.findAllByDateBetweenOrderByDate(LocalDate.parse("2021-05-01"), LocalDate.parse("2021-05-31"));
        return allT.stream().collect(Collectors.groupingBy(it -> it.getCategory()));
    }

}
