package com.aib.websystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aib.websystem.entity.Account;
import com.aib.websystem.entity.Role;
import com.aib.websystem.repository.AccountRepository;
import com.aib.websystem.repository.LocationRepository;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private LocationRepository locationRepository;

    @GetMapping("/")
    public String getAccountsPage(@RequestParam(defaultValue = "1") Integer page, Model model) {
        model.addAttribute("accounts", accountRepository.findAll(PageRequest.of(page - 1, 10)));
        return "/pages/account/index";
    }

    @GetMapping("/new")
    public String createAccountPage(Model model) {
        model.addAttribute("account", new Account());
        model.addAttribute("role_items", Role.MAP);
        model.addAttribute("location_items", locationRepository.findAll());
        return "/pages/account/new";
    }

    @GetMapping("/{username}")
    public String updateAccountPage(@PathVariable String username, Model model) {
        model.addAttribute("account", accountRepository.findById(username).orElse(null));
        model.addAttribute("role_items", Role.MAP);
        model.addAttribute("location_items", locationRepository.findAll());
        return "/pages/account/edit";
    }

    @PostMapping("/new")
    public String createAccount(Account account) {
        if (!accountRepository.existsById(account.getUsername())) {
            accountRepository.save(account);
        }
        return "redirect:/account/";
    }

    @PutMapping("/{username}")
    public String updateAccount(@PathVariable String username, Account newAccount) {
        if (accountRepository.existsById(username)) {
            Account originAccount = accountRepository.findById(username).get();
            originAccount.setPassword(newAccount.getPassword());
            originAccount.setRole(newAccount.getRole());
            originAccount.setLocation(newAccount.getLocation());
            accountRepository.save(originAccount);
        }
        return "redirect:/account/";
    }

    @DeleteMapping("/{username}")
    public String deleteAccount(@PathVariable String username) {
        if (accountRepository.existsById(username)) {
            accountRepository.deleteById(username);
        }
        return "redirect:/account/";
    }
}
