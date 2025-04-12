package com.aib.websystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aib.websystem.entity.Record;

public interface RecordRepository extends CrudRepository<Record, String>, PagingAndSortingRepository<Record, String>  {
}
