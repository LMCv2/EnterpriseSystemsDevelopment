package com.aib.websystem.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.aib.websystem.WebsystemApplication;
import com.aib.websystem.entity.Account;
import com.aib.websystem.entity.Fruit;
import com.aib.websystem.entity.Role;
import com.aib.websystem.entity.Location;
import com.aib.websystem.repository.AccountRepository;
import com.aib.websystem.repository.FruitRepository;
import com.aib.websystem.repository.LocationRepository;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "" })
public class CurrentAccountController extends HttpServlet {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private FruitRepository fruitRepository;

    @Autowired
    private LocationRepository locationRepository;

    private static final Logger logger = LoggerFactory.getLogger(WebsystemApplication.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        RequestDispatcher rd;
        if (accountRepository.count() == 0) {
            rd = req.getRequestDispatcher("/sys-init.jsp");
        } else if (req.getParameter("signinkey") != null) {
            rd = req.getRequestDispatcher("/sign-up.jsp");
        } else {
            rd = req.getRequestDispatcher("/sign-in.jsp");
        }
        rd.forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action.equals("sysinit")) {
            this.sysinit(req, res);
        } else if (action.equals("signup")) {
            this.signup(req, res);
        } else if (action.equals("signin")) {
            this.signin(req, res);
        } else if (action.equals("signout")) {
            this.signout(req, res);
        } else {
            this.doGet(req, res);
        }
    }

    public void sysinit(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        logger.info("init database");
        
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        accountRepository.save(new Account(username, password, Role.SENIOR_MANAGEMENT));

        fruitRepository.save(new Fruit("Apple"));
        fruitRepository.save(new Fruit("Banana"));
        fruitRepository.save(new Fruit("Orange"));
        fruitRepository.save(new Fruit("Strawberry"));
        fruitRepository.save(new Fruit("Mango"));

        // warehouse

        locationRepository.save(new Location("Source Warehouse", "SOURCE_WAREHOUSE"));
        locationRepository.save(new Location("Hong Kong Central Warehouse", "CENTRAL_WAREHOUSE"));

        res.sendRedirect("/");
    }

    public void signup(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    }

    public void signin(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Account account = accountRepository.findById(username).orElse(null);
        if (account.getPassword().equals(password)) {
            req.getSession().setAttribute("current_account", account);
            res.sendRedirect("/dashboard");
        } else {
            req.setAttribute("result", "Invalid username or password");
            res.sendRedirect("/");
        }
    }

    public void signout(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        session.invalidate();
        res.sendRedirect("/");
    }

}