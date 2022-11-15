package com.pma.web.controller;


import com.pma.web.model.House;
import com.pma.web.service.HouseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/house")
public class HouseController {

    @Autowired
    private HouseServiceImpl houseService;

    @PostMapping("/add")
    public ResponseEntity<House> addCar(@RequestBody House house) {
        return ResponseEntity.ok().body(this.houseService.addHouse(house));
    }

    @GetMapping("/{houseID}")
    public ResponseEntity<House> getHouse(@PathVariable("houseID") long houseID) {
        return ResponseEntity.ok().body(houseService.getHouse(houseID));
    }
//
//    public void removeHouse(long id);
//
//    public void updateHouse(long id, House house);
//
//    @GetMapping("/all")
//    public ResponseEntity<List<House>> getAllHouses() {
//        return ResponseEntity.ok().body(houseService.getAllHouses());
//    }
//    public List<House> getHouseByLandlord(long landlordID);
//
//    public List<House> getHouseByTenant(long tenantID);
//
//    public List<House> getHouseByCost(float min, float max);
//
//    public List<House> getHouseByRooms(Integer min, Integer max);
//
//    public List<House> getHousesInArea(String postcode);

}
