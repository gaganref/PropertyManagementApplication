package com.pma.web.controller;


import com.pma.web.model.House;
import com.pma.web.service.AddressServiceImpl;
import com.pma.web.service.HouseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/house")
public class HouseController {

    @Autowired
    private HouseServiceImpl houseService;

    @Autowired
    private AddressServiceImpl addressService;


    @GetMapping("/all")
    public String getAllHouse(Model model) {
        model.addAttribute("houses", houseService.getAllHouses());
        return "houses/allHouses";
    }

    @GetMapping("/{houseID}")
    public String getHouse(@PathVariable("houseID") long houseID, Model model) {
        House house = houseService.getHouse(houseID);
        model.addAttribute("house", house);
        model.addAttribute("address", addressService.getAddress(house.getAddress()));
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
    public String getHouseByCost(@PathVariable("min") float min, @PathVariable("max") float max, Model model) {
        model.addAttribute("houses", houseService.getHouseByCost(min, max));
        return "houses/allHouses";
    }

    @GetMapping("/byRooms/{min}/{max}")
    public String getHouseByRooms(@PathVariable("min") Integer min, @PathVariable("max") Integer max, Model model) {
        model.addAttribute("houses", houseService.getHouseByRooms(min, max));
        return "houses/allHouses";
    }

    @PostMapping("/add")
    public ResponseEntity<House> addHouse(@RequestBody House house) {
        return ResponseEntity.ok().body(this.houseService.addHouse(house));
    }

    @GetMapping("/{houseID}/edit")
    public String showHouseUpdateForm(@PathVariable("houseID") long houseID, Model model) {
        House house = houseService.getHouse(houseID);
        model.addAttribute("house", house);
        model.addAttribute("address", addressService.getAddress(house.getAddress()));
        return "houses/update";
    }

    @PostMapping("/{houseID}/update")
    public String updateHouse(@PathVariable("houseID") long houseID, @RequestBody House house,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            house.setHouseID(houseID);
            return "houses/update";
        }

        houseService.updateHouse(houseID, house);
        return "redirect:/house/all";
    }

    @GetMapping("/{houseID}/delete")
    public String removeHouse(@PathVariable("houseID") long houseID, Model model) {
//        this.houseService.removeHouse(houseID);
//        System.out.println("Works");
        return "redirect:/";
    }

}
