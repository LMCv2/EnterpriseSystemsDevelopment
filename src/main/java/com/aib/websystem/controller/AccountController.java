package com.aib.websystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aib.websystem.entity.Account;
import com.aib.websystem.repository.AccountRepository;

@Controller
@RequestMapping(path = "/account")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("")
    public String getFruits(Model model) {
        model.addAttribute("accounts", accountRepository.findAll(PageRequest.of(0, 10)));
        return "/pages/account/index";
    }

    @PostMapping("/create-account")
    public Account createAccount(@RequestParam String username,
            @RequestParam String password,
            @RequestParam String role) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setRole(role);
        return accountRepository.save(account);
    }

    @PostMapping("/update-account")
    public Account updateAccount(@RequestParam String username,
            @RequestParam String password) {
        Account account = accountRepository.findById(username).orElse(null);
        account.setPassword(password);
        return accountRepository.save(account);
    }

    @PostMapping("/delete-account")
    public boolean deleteAccount(@RequestParam String username) {
        if (accountRepository.existsById(username)) {
            accountRepository.deleteById(username);
            return true;
        } else {
            return false;
        }
    }

    @PostMapping("/get-all-account")
    public Iterable<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    @PostMapping("/get-account")
    public Account getAccount(@RequestParam String username) {
        return accountRepository.findById(username).orElse(null);
    }
}
