package com.aib.websystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aib.websystem.entity.Location;
import com.aib.websystem.entity.LocationType;

public interface LocationRepository extends CrudRepository<Location, Long>, PagingAndSortingRepository<Location, Long> {
    Page<Location> findByIdNotAndTypeNot(Long id, LocationType location, Pageable pageable);
}

// public interface LocationRepository extends JpaRepository<Location, Long>,
// JpaSpecificationExecutor<Location> {
// }