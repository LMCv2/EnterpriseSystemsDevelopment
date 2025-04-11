package com.aib.websystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aib.websystem.entity.Borrowing;

public interface BorrowingRepository extends CrudRepository<Borrowing, String>, PagingAndSortingRepository<Borrowing, String>  {
}
