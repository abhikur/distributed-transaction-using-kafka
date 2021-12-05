package com.example.distributedtransaction.service;

import com.example.distributedtransaction.model.Account;
import com.example.distributedtransaction.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public Integer deductAmount(Integer id, Integer amount) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            int currentBalance = account.get().getBalance();
            account.get().setBalance(currentBalance - amount);
            accountRepository.save(account.get());
            return account.get().getBalance();
        }
        return null;
    }

    public Integer getBalance(int id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            return account.get().getBalance();
        } else {
            return null;
        }
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public void createAccount() {
        Account account = new Account();
        account.setBalance(1000);
        accountRepository.save(account);
    }
}
