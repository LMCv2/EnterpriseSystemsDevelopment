package com.aib.websystem.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aib.websystem.entity.Account;
import com.aib.websystem.entity.Location;
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
    public String getAccountsPage(Model model) {
        model.addAttribute("accounts", accountRepository.findAll(PageRequest.of(0, 10)));
        return "/pages/account/index";
    }

    @GetMapping("/new")
    public String createAccountPage(Model model) {
        model.addAttribute("account", new Account());
        model.addAttribute("role_items", Role.MAP);
        model.addAttribute("location_items",
                locationRepository.findAll().stream().collect(Collectors.toMap(Location::getId, Location::getName)));
        return "/pages/account/new";
    }

    @GetMapping("/{username}")
    public String updateAccountPage(@PathVariable String username, Model model) {
        model.addAttribute("account_role_type", Role.MAP);
        model.addAttribute("account", accountRepository.findById(username).orElse(null));
        return "/pages/account/edit";
    }

    @PostMapping("/new")
    public String createAccount(Account account, Model model) {
        accountRepository.save(account);
    System.out.println(account);
        return "redirect:/account/";
    }

    @PutMapping("/{username}")
    public String updateAccount(
            @PathVariable String username,
            @ModelAttribute("account") Account account) {
        accountRepository.save(account);
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
