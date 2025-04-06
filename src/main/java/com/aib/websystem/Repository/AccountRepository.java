package com.aib.websystem.Repository;

import org.springframework.data.repository.CrudRepository;

import com.aib.websystem.Entity.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {

}
