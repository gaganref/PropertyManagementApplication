package com.pma.web.controller;


import com.pma.web.model.House;
import com.pma.web.service.HouseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/house")
public class HouseController {

    @Autowired
    private HouseServiceImpl houseService;


    @GetMapping("/all")
    public ResponseEntity<List<House>> getAllHouse() {
        return ResponseEntity.ok().body(houseService.getAllHouses());
    }

    @GetMapping("/{houseID}")
    public ResponseEntity<House> getHouse(@PathVariable("houseID") long houseID) {
        return ResponseEntity.ok().body(houseService.getHouse(houseID));
    }

    @GetMapping("/byLandlord/{landlordID}")
    public ResponseEntity<List<House>> getHouseByLandlord(@PathVariable("landlordID") long landlordID) {
        //TODO
        return ResponseEntity.ok().body(houseService.getAllHouses());
    }

    @GetMapping("/byPostcode/{postcode}")
    public ResponseEntity<List<House>> getHouseByPostCode(@PathVariable("postcode") String postcode) {
        return ResponseEntity.ok().body(houseService.getAllHouses());
    }

    @GetMapping("/byCost/{min}/{max}")
    public ResponseEntity<List<House>> getHouseByCost(@PathVariable("min") float min, @PathVariable("max") float max) {
        return ResponseEntity.ok().body(houseService.getHouseByCost(min, max));
    }

    @GetMapping("/byRooms/{min}/{max}")
    public ResponseEntity<List<House>> getHouseByRooms(@PathVariable("min") Integer min, @PathVariable("max") Integer max) {
        return ResponseEntity.ok().body(houseService.getHouseByRooms(min, max));
    }

    @PutMapping("/{houseID}/update/cost/{pppm}")
    public ResponseEntity<House> updateCost(@PathVariable("houseID") long houseID, @PathVariable("pppm") float pppm) {
        return ResponseEntity.ok().body(this.houseService.updateCost(houseID, pppm));
    }

    @DeleteMapping("/delete/{houseID}")
    public HttpStatus removeCar(@PathVariable("houseID") long houseID) {
        this.houseService.removeHouse(houseID);
        return HttpStatus.OK;
    }
}
