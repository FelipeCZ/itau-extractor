package com.zanichelli.felipe.itauextrator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.glassfish.jersey.internal.guava.Lists;
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
        transactionService.resetMonth(month, year);
        BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
        var allLines = new ArrayList<String[]>();
        String line;
        while ((line = br.readLine()) != null) {
            var splited = line.split(";");
            allLines.add(splited);
        }
        transactionService.classify(allLines);            
        return transactionService.getAllMonthlyTransactionsGroupedByCategory(month, year);
	}
}
