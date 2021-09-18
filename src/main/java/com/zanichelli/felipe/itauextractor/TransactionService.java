package com.zanichelli.felipe.itauextractor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryService categoryService;

    public void resetMonth(final int month, final int year) {
        transactionRepository.findAllByDateBetweenOrderByDate(toFirstDayOfMonth(month, year), toLastDayOfMonth(month, year));
    }

    public Transaction build(final String date, final String softDescriptor, final String amount) {
        Transaction t = new Transaction();
        t.setDate(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        t.setSoftDescriptor(softDescriptor);
        t.setAmount(new BigDecimal(amount.replaceAll("\\.", "").replaceAll(",", ".")).setScale(2));
        return t;
    }

    public List<Transaction> classify(final List<String[]> transactionDetailList) {
        var classifier = categoryService.newClassifier();
        var transactionList = new ArrayList<Transaction>();
        for (String[] transactionDetail : transactionDetailList) {
            Transaction t = build(transactionDetail[0], transactionDetail[1], transactionDetail[2]);
            t.setCategory(classifier.findCategory(t.getSoftDescriptor()));
            transactionList.add(transactionRepository.save(t));
        }
        return transactionList;
    }

    public Map<String, List<Transaction>> getAllMonthlyTransactionsGroupedByCategory(int month, int year) {
        var allT = transactionRepository.findAllByDateBetweenOrderByDate(toFirstDayOfMonth(month, year), toLastDayOfMonth(month, year));
        return allT.stream().collect(Collectors.groupingBy(it -> 
            Optional.ofNullable(it.getCategory()).map(cat -> cat.getName()).orElse("Uncategorized")
        ));
    }

    private LocalDate toFirstDayOfMonth(final int month, final int year) {
        return LocalDate.of(year, month, 1);
    }

    private LocalDate toLastDayOfMonth(final int month, final int year) {
        return toFirstDayOfMonth(month, year).plusMonths(1).minusDays(1);
    }

}
