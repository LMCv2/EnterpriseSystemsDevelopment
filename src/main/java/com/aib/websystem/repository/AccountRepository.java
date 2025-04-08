package com.aib.websystem.repository;

import org.springframework.data.repository.CrudRepository;

import com.aib.websystem.entity.Account;

public interface AccountRepository extends CrudRepository<Account, String> {
}
