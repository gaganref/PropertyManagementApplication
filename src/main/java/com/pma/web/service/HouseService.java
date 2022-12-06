package com.pma.web.service;

import com.pma.web.model.House;
import com.pma.web.model.Landlord;

import java.math.BigDecimal;
import java.util.List;

public interface HouseService {

    public void addHouse(House house);

    public House getHouse(long id);

    public void removeHouse(long id);

    public void updateHouse(long id, House house);

    public List<House> getAllHouses();

    public List<House> getHouseByLandlord(Landlord landlord);

    public List<House> getHouseByCost(BigDecimal min, BigDecimal max);

    public List<House> getHouseByRooms(Integer min, Integer max);
}
