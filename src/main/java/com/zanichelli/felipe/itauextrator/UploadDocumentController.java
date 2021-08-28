package com.zanichelli.felipe.itauextrator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.SneakyThrows;

@RestController
public class UploadDocumentController {

    private static final List<String> LANCAMENTOS_IGNORADOS = new ArrayList<String>();

    @Autowired
    private TransactionService transactionService;

    static {
        LANCAMENTOS_IGNORADOS.add("RES APLIC AUT MAIS");
        LANCAMENTOS_IGNORADOS.add("APL APLIC AUT MAIS");
        LANCAMENTOS_IGNORADOS.add("SALDO FINAL");
        LANCAMENTOS_IGNORADOS.add("SALDO PARCIAL");
        LANCAMENTOS_IGNORADOS.add("REND PAGO APLIC AUT MAIS");
    }
    
    @PostMapping("/upload/extract/month")
    @ResponseBody
    @SneakyThrows
	public Map<Category, List<Transaction>> handleFileUpload(@RequestParam("file") MultipartFile file) {
        BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
        String line;
        while ((line = br.readLine()) != null) {
            var splited = line.split(";");
            var estabelecimento = splited[1];
            if (!LANCAMENTOS_IGNORADOS.contains(estabelecimento)) {
                transactionService.classify(splited);
            }
        }
        return transactionService.getAllMonthlyTransactionsGroupedByCategory(05, 2021);
	}
}
