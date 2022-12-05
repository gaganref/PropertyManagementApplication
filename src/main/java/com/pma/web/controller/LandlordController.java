package com.pma.web.controller;


import java.util.ArrayList;
import java.util.List;

import com.pma.web.model.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pma.web.model.Landlord;
import com.pma.web.service.LandlordServiceImpl;

import javax.validation.Valid;

@Controller
@RequestMapping("/landlord")
public class LandlordController {
	@Autowired
    private LandlordServiceImpl landlordService;

	@GetMapping("/showAdd")
	public String showAddLandlord(Landlord landlord, Model model) {
		model.addAttribute("landlord", new Landlord());
		return "landlord/add";
	}

	@PostMapping("/add")
	public String addLandlord(@Valid Landlord landlord, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "landlord/add";
		}

		landlordService.addLandlord(landlord);
		return "redirect:/landlord/getall";
	}

	@GetMapping("/{landlordId}")
	public String getLandlord(@PathVariable("landlordId") int landlordid, Model model) {
		List<Landlord> landlordList = new ArrayList<>();
		landlordList.add(landlordService.getLandlord(landlordid));
		model.addAttribute("landlords", landlordList);
		return "landlord/allLandlords";
	}

    @GetMapping("/getall")
	public String getAllLandlords(Model model) {
		model.addAttribute("landlords", landlordService.getAllLandlords());
		return "landlord/allLandlords";
	}

	@GetMapping("/{landlordid}/edit")
	public String showUpdateLandlord(@PathVariable("landlordid") long landlordid, Model model) {
		Landlord landlord = landlordService.getLandlord(landlordid);
		model.addAttribute("landlord", landlord);
		return "landlord/update";
	}

	@PostMapping("/{landlordid}/update")
	public String updateLandlord(@PathVariable("landlordid") long landlordid, @Valid Landlord landlord,
							  BindingResult result, Model model) {
		if (result.hasErrors()) {
			landlord.setLandlordId(landlordid);
			return "landlord/update";
		}

		landlordService.updateLandlord(landlordid, landlord);
		return "redirect:/landlord/getall";
	}
	
    @GetMapping("/{landlordid}/delete")
	public String removeLandlord(@PathVariable("landlordid") int landlordid, Model model) {
		this.landlordService.removeLandlord(landlordid);
		return "redirect:/landlord/getall";
	}
}
