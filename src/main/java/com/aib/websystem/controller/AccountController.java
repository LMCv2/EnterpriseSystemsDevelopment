package com.aib.websystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aib.websystem.entity.Account;
import com.aib.websystem.repository.AccountRepository;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/accounts/{id}")
    public Account getAccount(@PathVariable String id) {
        return accountRepository.findById(id).orElse(null);
    }

    @PostMapping("/create-account")
    public Account createAccount(@RequestParam String username,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setFirstName(firstName);
        account.setLastName(lastName);
        return accountRepository.save(account);
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
            @RequestParam String password) {
        Account account = accountRepository.findById(username).orElse(null);
        if (account.getPassword().equals(password)) {
            return "Login successful for user: " + account.getUsername();
        } else {
            return "Invalid username or password";
        }
    }
}
