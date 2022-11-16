package com.pma.web.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/landlord")
public class LandlordController {
	@Autowired
    private LandlordServiceImpl landlordService;

    @PostMapping("/add")
    public ResponseEntity<Landlord> addLandlord(@RequestBody Landlord landlord) {
        return ResponseEntity.ok().body(this.landlordService.addLandlord(landlord));
    }

    @GetMapping("/{landlordId}")
    public ResponseEntity<Landlord> getLandlord(@PathVariable("landlordId") int landlordid) {
        return ResponseEntity.ok().body(landlordService.getLandlord(landlordid));
    }
    
    @GetMapping("/getall")
	public ResponseEntity<List<Landlord>> getAllLandlords() {
		return ResponseEntity.ok().body(landlordService.getAllLandlords());
	}
    
	@PutMapping("/{landlordid}/update")
	public ResponseEntity<Landlord> updatelandlord(@RequestBody Landlord landlord, @PathVariable("landlordid")int landlordid) {
		return ResponseEntity.ok().body(this.landlordService.updateLandlord(landlordid, landlord));
	}
	
    @DeleteMapping("/{landlordid}/delete")
	public HttpStatus removeLandlord(@PathVariable("landlordid") int landlordid) {
		this.landlordService.removeLandlord(landlordid);
		return HttpStatus.OK;
	}
}
