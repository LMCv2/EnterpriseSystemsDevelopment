package com.aib.websystem.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.aib.websystem.entity.Account;
import com.aib.websystem.repository.AccountRepository;

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/sign-in.jsp");
        rd.forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action.equals("signup")) {
            this.signup(req, res);
        } else if (action.equals("signin")) {
            this.signin(req, res);
        } else if (action.equals("logout")) {
            this.logout(req, res);
        } else {
            this.doGet(req, res);
        }
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

    public void logout(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        session.invalidate();
        res.sendRedirect("/sign-in.jsp");
    }
}
