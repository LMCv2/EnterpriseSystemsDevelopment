package com.aib.websystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aib.websystem.entity.Account;

public interface AccountRepository extends CrudRepository<Account, String>, PagingAndSortingRepository<Account, String>  {
}
