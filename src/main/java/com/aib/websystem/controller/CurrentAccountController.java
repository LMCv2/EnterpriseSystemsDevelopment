package com.aib.websystem.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import com.aib.websystem.WebsystemApplication;
import com.aib.websystem.entity.Account;
import com.aib.websystem.entity.Borrowing;
import com.aib.websystem.entity.Fruit;
import com.aib.websystem.entity.Role;
import com.aib.websystem.entity.Stock;
import com.aib.websystem.entity.Location;
import com.aib.websystem.repository.AccountRepository;
import com.aib.websystem.repository.BorrowingRepository;
import com.aib.websystem.repository.FruitRepository;
import com.aib.websystem.repository.LocationRepository;
import com.aib.websystem.repository.StockRepository;

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

    @Autowired
    private StockRepository stockRepository;
    
    @Autowired
    private BorrowingRepository borrowingRepository;

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
        Iterator<Account> account = accountRepository.findAll().iterator();
        locationRepository.save(new Location("Source Warehouse 1", "Hong Kong", "SOURCE_WAREHOUSE", account.next()));
        locationRepository.save(new Location("Central Warehouse 1", "Hong Kong", "CENTRAL_WAREHOUSE", null));
        locationRepository.save(new Location("Central Warehouse 2", "Hong Kong", "CENTRAL_WAREHOUSE", null));
        locationRepository.save(new Location("Central Warehouse 3", "London", "CENTRAL_WAREHOUSE", null));
        // bakery shop
        locationRepository.save(new Location("Bakery Shop 1", "Hong Kong", "SHOP", null));

        //stock
        for(Fruit fruit : fruitRepository.findAll()) {
            stockRepository.save(new Stock(fruit, 100));
        }

        //borrowing
        Fruit fruit = fruitRepository.findAll().iterator().next();
        
        // Create a specification that returns all locations
        Specification<Location> allLocations = (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        Iterator<Location> it = locationRepository.findAll(allLocations).iterator();
        
        borrowingRepository.save(new Borrowing(fruit, 100, it.next() , it.next()));

        res.sendRedirect("/");
    }


    public void signup(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    }

    public void signin(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Optional<Account> account = accountRepository.findById(username);
        if (account.isPresent() && account.get().getPassword().equals(password)) {
            HttpSession session = req.getSession();
            session.setAttribute("current_account", account.get());
            switch (account.get().getRole()) {
                case CENTRAL_WAREHOUSE_STAFF:
                    session.setAttribute("permissions", Set.of("dashboard"));
                    res.sendRedirect("/dashboard");
                    break;
                case SENIOR_MANAGEMENT:
                    session.setAttribute("permissions", Set.of("dashboard", "fruit", "account"));
                    res.sendRedirect("/dashboard");
                    break;
                case SHOP_STAFF:
                    session.setAttribute("permissions", Set.of("dashboard", "stock", "borrowing"));
                    res.sendRedirect("/dashboard");
                    break;
                case SOURCE_WAREHOUSE_STAFF:
                    session.setAttribute("permissions", Set.of("dashboard"));
                    res.sendRedirect("/dashboard");
                    break;
            }
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
