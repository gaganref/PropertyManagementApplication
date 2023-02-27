package com.pma.web.controller;


import com.pma.web.model.House;
import com.pma.web.model.Landlord;
import com.pma.web.service.HouseServiceImpl;
import com.pma.web.service.LandlordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/house")
public class HouseController {

    @Autowired
    private HouseServiceImpl houseService;

    @Autowired
    private LandlordServiceImpl landlordService;


    @GetMapping("/all")
    public String getAllHouse(Model model) {
        model.addAttribute("houses", houseService.getAllHouses());
        return "houses/allHouses";
    }

    @GetMapping("/{houseID}")
    public String getHouse(@PathVariable("houseID") long houseID, Model model) {
        List<House> houses = new ArrayList<>();
        houses.add(houseService.getHouse(houseID));
        model.addAttribute("houses", houses);
        return "houses/allHouses";
    }

    @GetMapping("/byLandlord/{landlordID}")
    public String getHousesOfLandlord(Model model, @PathVariable("landlordID") long landlordID) {
        Landlord landlord = landlordService.getLandlord(landlordID);
        model.addAttribute("houses", houseService.getHouseByLandlord(landlord));
        return "houses/allHouses";
    }

    @GetMapping("/byCost/")
    public String getHouseByCost(BigDecimal min, BigDecimal max, Model model) {
        model.addAttribute("houses", houseService.getHouseByCost(min, max));
        return "houses/allHouses";
    }

    @GetMapping("/byRooms/")
    public String getHouseByRooms(Integer min, Integer max, Model model) {
        model.addAttribute("houses", houseService.getHouseByRooms(min, max));
        return "houses/allHouses";
    }

    @GetMapping("/showAdd")
    public String showAddHouse(House house, Model model) {
        model.addAttribute("house", new House());
        model.addAttribute("landlords", landlordService.getAllLandlords());
        return "houses/add";
    }

    @PostMapping("/add")
    public String addHouse(@Valid House house, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("landlords", landlordService.getAllLandlords());
            return "houses/add";
        }

        houseService.addHouse(house);
        return "redirect:/house/all";
    }

    @GetMapping("/showAdd/landlord/{landlordID}")
    public String showAddHouseLandlord(House house, Model model, @PathVariable long landlordID) {

        House newHouse = new House();
        Landlord newLandlord = landlordService.getLandlord(landlordID);
        newHouse.setLandlord(newLandlord);

        model.addAttribute("house", newHouse);
        model.addAttribute("inLandlord", newLandlord);
        return "houses/addHouseWithLandlord";
    }

    @PostMapping("/add/landlord/{landlordID}")
    public String addHouseLandlord(@Valid House house, BindingResult result, Model model, @PathVariable long landlordID) {

        if (result.hasErrors()) {
            Landlord newLandlord = landlordService.getLandlord(landlordID);
            house.setLandlord(newLandlord);
            model.addAttribute("inLandlord", newLandlord);
            return "houses/addHouseWithLandlord";
        }

        houseService.addHouse(house);
        return "redirect:/landlord/"+landlordID;
    }

    @GetMapping("/{houseID}/edit")
    public String showHouseUpdateForm(@PathVariable("houseID") long houseID, Model model) {
        House house = houseService.getHouse(houseID);
        model.addAttribute("house", house);
        model.addAttribute("landlords", landlordService.getAllLandlords());
        return "houses/update";
    }

    @PostMapping("/{houseID}/update")
    public String updateHouse(@PathVariable("houseID") long houseID, @Valid House house,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            house.setHouseID(houseID);
            model.addAttribute("landlords", landlordService.getAllLandlords());
            return "houses/update";
        }

        houseService.updateHouse(houseID, house);
        return "redirect:/house/all";
    }

    @GetMapping("/{houseID}/delete")
    public String removeHouse(@PathVariable("houseID") long houseID, Model model) {
        this.houseService.removeHouse(houseID);
        return "redirect:/house/all";
    }

}
