package com.aib.websystem.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.aib.websystem.entity.Event;
import com.aib.websystem.entity.EventStatus;
import com.aib.websystem.entity.EventType;
import com.aib.websystem.entity.Fruit;
import com.aib.websystem.entity.Location;
import com.aib.websystem.entity.ReserveNeedDTO;
import com.aib.websystem.entity.SeasonalConsumptionDTO;

public interface EventRepository extends CrudRepository<Event, Long>, PagingAndSortingRepository<Event, Long> {
    // event
    Page<Event> findByStatus(EventStatus status, Pageable pageable);

    @Query("select e from Event e where e.fromLocation = ?1 OR e.throughLocation = ?1 OR e.toLocation = ?1")
    Page<Event> findByLocation(Location location, Pageable pageable);

    @Query("select e from Event e where (e.fromLocation = ?1 OR e.throughLocation = ?1 OR e.toLocation = ?1) and e.status = ?2")
    Page<Event> findByLocationAndStatus(Location location, EventStatus status, Pageable pageable);

    // Dashboard queries
    Long countByCreateDateBetween(Date startDate, Date endDate);

    @Query("SELECT SUM(e.quantity) FROM Event e WHERE e.createDate BETWEEN ?1 AND ?2")
    Long sumQuantityByCreateDateBetween(Date startDate, Date endDate);

    Long countByStatusNotIn(List<EventStatus> statuses);

    // grouped events
    @Query("select distinct e.fruit, e.timePeriod, e.fromLocation, e.throughLocation from Event e")
    Page<Object[]> findDistinct(Pageable pageable);

    @Query("select distinct e.fruit, e.timePeriod, e.fromLocation, e.throughLocation from Event e where e.status = ?1")
    Page<Object[]> findDistinctByStatus(EventStatus status, Pageable pageable);

    @Query("select distinct e.fruit, e.timePeriod, e.fromLocation, e.throughLocation from Event e where (e.status = DELIVEREDCENTRAL or e.status = SHIPPED or e.status = DELIVERED)")
    Page<Object[]> findDistinctByDeliveredStatus(Pageable pageable);

    @Query("select distinct e.fruit, e.timePeriod, e.fromLocation, e.throughLocation from Event e where e.fromLocation = ?1")
    Page<Object[]> findDistinctByFromLocation(Location fromLocation, Pageable pageable);

    @Query("select distinct e.fruit, e.timePeriod, e.fromLocation, e.throughLocation from Event e where e.fromLocation = ?1 and e.status = ?2")
    Page<Object[]> findDistinctByFromLocationAndStatus(Location fromLocation, EventStatus status, Pageable pageable);

    @Query("select distinct e.fruit, e.timePeriod, e.fromLocation, e.throughLocation from Event e where e.fromLocation = ?1 and (e.status = DELIVEREDCENTRAL or e.status = SHIPPED or e.status = DELIVERED)")
    Page<Object[]> findDistinctByFromLocationAndDeliveredStatus(Location fromLocation, Pageable pageable);

    @Query("select distinct e.fruit, e.timePeriod, e.fromLocation, e.throughLocation from Event e where e.throughLocation = ?1")
    Page<Object[]> findDistinctByThroughLocation(Location throughLocation, Pageable pageable);

    @Query("select distinct e.fruit, e.timePeriod, e.fromLocation, e.throughLocation from Event e where e.throughLocation = ?1 and e.status = ?2")
    Page<Object[]> findDistinctByThroughLocationAndStatus(Location throughLocation, EventStatus status, Pageable pageable);

    @Query("select distinct e.fruit, e.timePeriod, e.fromLocation, e.throughLocation from Event e where e.throughLocation = ?1 and (e.status = DELIVEREDCENTRAL or e.status = SHIPPED or e.status = DELIVERED)")
    Page<Object[]> findDistinctByThroughLocationAndDeliveredStatus(Location throughLocation, Pageable pageable);

    List<Event> findByFruitAndTimePeriodAndFromLocationAndThroughLocation(Fruit fruit, Integer timePeriod, Location fromLocation, Location throughLocation);

    // for preventing duplicates
    List<Event> findByFruitAndTimePeriodAndFromLocationAndThroughLocationAndStatusNot(Fruit fruit, Integer timePeriod, Location fromLocation, Location throughLocation, EventStatus eventStatus);

    // reserve needs report
    @Query("SELECT new com.aib.websystem.entity.ReserveNeedDTO(e.toLocation.name, e.fruit.name, SUM(e.quantity)) FROM Event e WHERE e.createDate BETWEEN ?1 AND ?2 GROUP BY e.toLocation.name, e.fruit.name")
    Page<ReserveNeedDTO> findReserveNeedsByShop(Date startDate, Date endDate, Pageable pageable);

    @Query("SELECT new com.aib.websystem.entity.ReserveNeedDTO(e.toLocation.city, e.fruit.name, SUM(e.quantity)) FROM Event e WHERE e.createDate BETWEEN ?1 AND ?2 GROUP BY e.toLocation.city, e.fruit.name")
    Page<ReserveNeedDTO> findReserveNeedsByCity(Date startDate, Date endDate, Pageable pageable);

    @Query("SELECT new com.aib.websystem.entity.ReserveNeedDTO(e.toLocation.country, e.fruit.name, SUM(e.quantity)) FROM Event e WHERE e.createDate BETWEEN ?1 AND ?2 GROUP BY e.toLocation.country, e.fruit.name")
    Page<ReserveNeedDTO> findReserveNeedsByCountry(Date startDate, Date endDate, Pageable pageable);

    // seasonal consumption report
    @Query("SELECT new com.aib.websystem.entity.SeasonalConsumptionDTO(e.fromLocation.name, e.year, e.season, e.fruit.name, SUM(e.quantity)) FROM Event e WHERE e.eventType = com.aib.websystem.entity.EventType.CONSUMPTION AND e.createDate BETWEEN ?1 AND ?2 GROUP BY e.fromLocation.name, e.year, e.season, e.fruit.name")
    Page<SeasonalConsumptionDTO> findSeasonalConsumptionByShop(Date startDate, Date endDate, Pageable pageable);

    @Query("SELECT new com.aib.websystem.entity.SeasonalConsumptionDTO(e.fromLocation.city, e.year, e.season, e.fruit.name, SUM(e.quantity)) FROM Event e WHERE e.eventType = com.aib.websystem.entity.EventType.CONSUMPTION AND e.createDate BETWEEN ?1 AND ?2 GROUP BY e.fromLocation.city, e.year, e.season, e.fruit.name")
    Page<SeasonalConsumptionDTO> findSeasonalConsumptionByCity(Date startDate, Date endDate, Pageable pageable);

    @Query("SELECT new com.aib.websystem.entity.SeasonalConsumptionDTO(e.fromLocation.country, e.year, e.season, e.fruit.name, SUM(e.quantity)) FROM Event e WHERE e.eventType = com.aib.websystem.entity.EventType.CONSUMPTION AND e.createDate BETWEEN ?1 AND ?2 GROUP BY e.fromLocation.country, e.year, e.season, e.fruit.name")
    Page<SeasonalConsumptionDTO> findSeasonalConsumptionByCountry(Date startDate, Date endDate, Pageable pageable);

    // delivery forecast report
    List<Event> findByEventTypeAndStatusAndCreateDateBetween(EventType eventType, EventStatus status, Date startDate, Date endDate);
}
