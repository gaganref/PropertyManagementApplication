package com.pma.web.repository;

import com.pma.web.model.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

    List<House> findAll();

    Optional<House> findByHouseID(long houseID);

    List<House> findByLandlord(long landlordID);

    List<House> findHouseByNoOfRoomsBetween(Integer min, Integer max);

    List<House> findHouseByPppmBetween(float min, float max);


}