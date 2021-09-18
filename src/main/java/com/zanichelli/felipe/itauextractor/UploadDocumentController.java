package com.zanichelli.felipe.itauextractor;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.SneakyThrows;

@RestController
public class UploadDocumentController {

    @Autowired
    private TransactionService transactionService;
    
    @PostMapping("/upload/extract/month/{month}/year/{year}")
    @ResponseBody
    @SneakyThrows
	public Map<String, List<Transaction>> handleFileUpload(@RequestParam("file") final MultipartFile file, @PathVariable final int month, @PathVariable int year) {
        transactionService.processFile(file.getInputStream(), month, year);
        return transactionService.getAllMonthlyTransactionsGroupedByCategory(month, year);
	}
}
