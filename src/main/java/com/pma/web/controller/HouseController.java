package com.pma.web.controller;


import com.pma.web.model.House;
import com.pma.web.service.HouseServiceImpl;
import com.pma.web.service.LandlordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

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
        House house = houseService.getHouse(houseID);
        model.addAttribute("house", house);
        return "houses/house";
    }


    @GetMapping("/byLandlord/{landlordID}")
    public String getHouseByLandlord(@PathVariable("landlordID") long landlordID, Model model) {
        model.addAttribute("houses", houseService.getHouseByLandlord(landlordID));
        return "houses/allHouses";
    }

    //TODO:
    @GetMapping("/byPostcode/{postcode}")
    public String getHouseByPostCode(@PathVariable("postcode") String postcode, Model model) {
        model.addAttribute("houses", houseService.getAllHouses());
        return "houses/allHouses";
    }

    @GetMapping("/byCost/{min}/{max}")
    public String getHouseByCost(@PathVariable("min") BigDecimal min, @PathVariable("max") BigDecimal max, Model model) {
        model.addAttribute("houses", houseService.getHouseByCost(min, max));
        return "houses/allHouses";
    }

    @GetMapping("/byRooms/{min}/{max}")
    public String getHouseByRooms(@PathVariable("min") Integer min, @PathVariable("max") Integer max, Model model) {
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
