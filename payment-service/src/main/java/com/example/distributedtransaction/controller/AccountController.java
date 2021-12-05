package com.example.distributedtransaction.controller;

import com.example.distributedtransaction.model.Account;
import com.example.distributedtransaction.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<String> createAccount() {
        accountService.createAccount();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/balance")
    public ResponseEntity<Integer> balance(@RequestParam int id) {
        Integer balance = accountService.getBalance(id);
        if (balance != null) {
            return ResponseEntity.ok(balance);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }
}
