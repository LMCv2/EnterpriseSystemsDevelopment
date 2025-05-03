package com.aib.websystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aib.websystem.entity.Account;
import com.aib.websystem.entity.Role;

public interface AccountRepository extends CrudRepository<Account, String>, PagingAndSortingRepository<Account, String> {
    Page<Account> findByRole(Role role, Pageable pageable);
}
