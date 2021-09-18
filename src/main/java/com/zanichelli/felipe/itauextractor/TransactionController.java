package com.zanichelli.felipe.itauextractor;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    
    @GetMapping("/findall/month/{month}/year/{year}")
    @ResponseBody
    public Map<String, List<Transaction>> getAllByMonthAndYear(@PathVariable final int month, @PathVariable int year) {
        return transactionService.getAllMonthlyTransactionsGroupedByCategory(month, year);
    }


}