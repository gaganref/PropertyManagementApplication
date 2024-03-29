package com.pma.web.service;

import com.pma.web.exception.ModelAddException;
import com.pma.web.exception.ModelEmptyListException;
import com.pma.web.exception.ModelNotFoundException;
import com.pma.web.exception.ModelUpdateException;
import com.pma.web.model.House;
import com.pma.web.model.Landlord;
import com.pma.web.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseRepository houseRepository;

    @Override
    public void addHouse(House house) {
        try {
            houseRepository.save(house);
        } catch (Exception e) {
            throw new ModelAddException("Couldn't add the model House, please add proper details");
        }
    }

    @Override
    public House getHouse(long id) {
        try {
            Optional<House> outHouse = houseRepository.findByHouseID(id);
            if(outHouse.isPresent()){
                return outHouse.get();
            }
            else{
                throw new ModelNotFoundException("Couldn't find the house of id: " + id);
            }
        } catch (Exception e) {
            throw new ModelNotFoundException("Couldn't find the house of id: " + id);
        }
    }

    @Override
    public void removeHouse(long id) {
        try {
            Optional<House> outHouse = houseRepository.findByHouseID(id);
            if(outHouse.isPresent()){
                if(outHouse.get().getTenancyInfo() != null){
                    outHouse.get().getTenancyInfo().setHouse(null);
                    outHouse.get().setTenancyInfo(null);
                }
                houseRepository.delete(outHouse.get());
            }
            else{
                throw new ModelNotFoundException("Couldn't find the house of id: " + id);
            }
        } catch (Exception e) {
            throw new ModelNotFoundException("Couldn't remove house of id: " + id + " as it is not present");
        }
    }

    @Override
    public void updateHouse(long id, House house) {
        try {
            Optional<House> outHouse = houseRepository.findByHouseID(id);
            if(outHouse.isPresent()){
                House houseToUpdate = outHouse.get();

                houseToUpdate.setLandlord(house.getLandlord());
                houseToUpdate.setAddress(house.getAddress());
                houseToUpdate.setNoOfRooms(house.getNoOfRooms());
                houseToUpdate.setCost(house.getCost());
            }
            else{
                throw new ModelUpdateException("Couldn't update house of id: " + id);
            }
        } catch (Exception e) {
            throw new ModelUpdateException("Couldn't update house of id: " + id + " as it is not present");
        }
    }

    @Override
    public List<House> getAllHouses() {
        try {
            return houseRepository.findAll();
        } catch (Exception e) {
            throw new ModelEmptyListException("Error retrieving houses... please try again");
        }
    }

    @Override
    public List<House> getHouseByLandlord(Landlord landlord) {
        try {
            return houseRepository.findByLandlord(landlord);
        } catch (Exception e) {
            throw new ModelEmptyListException("No houses found for the given landlord");
        }
    }

    @Override
    public List<House> getHouseByCost(BigDecimal min, BigDecimal max) {
        try {
            return houseRepository.findHouseByCostBetween(min, max);
        } catch (Exception e) {
            throw new ModelEmptyListException("No houses found between the given cost");
        }
    }

    @Override
    public List<House> getHouseByRooms(Integer min, Integer max) {
        try {
            return houseRepository.findHouseByNoOfRoomsBetween(min, max);
        } catch (Exception e) {
            throw new ModelEmptyListException("No houses found between the given rooms");
        }
    }
}
