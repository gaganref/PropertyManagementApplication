package com.pma.web.service;

import com.pma.web.model.House;

import java.math.BigDecimal;
import java.util.List;

public interface HouseService {

    public House addHouse(House house);

    public House getHouse(long id);

    public void removeHouse(long id);

    public House updateHouse(long id, House house);

    public House updateCost(long id, BigDecimal pppm);

    public House updateRooms(long id, Integer no_of_rooms);

    public List<House> getAllHouses();

    public List<House> getHouseByLandlord(long landlordID);

    public List<House> getHouseByTenant(long tenantID);

    public List<House> getHouseByCost(BigDecimal min, BigDecimal max);

    public List<House> getHouseByRooms(Integer min, Integer max);

    public List<House> getHousesInArea(String postcode);
}
