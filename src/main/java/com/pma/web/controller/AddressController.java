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

import com.pma.web.model.Address;
import com.pma.web.service.AddressServiceImpl;

@RestController
@RequestMapping("/address")
public class AddressController {
	@Autowired
    private AddressServiceImpl addressService;

    @PostMapping("/add")
    public ResponseEntity<Address> addAddress(@RequestBody Address address) {
        return ResponseEntity.ok().body(this.addressService.addAddress(address));
    }

    @GetMapping("/{AddressID}")
    public ResponseEntity<Address> getAddress(@PathVariable("AddressID") int addressId) {
        return ResponseEntity.ok().body(addressService.getAddress(addressId));
    }
    
    @GetMapping("/byPostcode/{PostCode}")
	public ResponseEntity<List<Address>> getAddressByPostCode(@PathVariable("PostCode") String postcode) {
		return ResponseEntity.ok().body(addressService.getAllAddress(postcode));
	}
    
	@PutMapping("/{addressid}")
	public ResponseEntity<Address> updateAddress(@RequestBody Address address, @PathVariable("addressid") int addressId) {
		return ResponseEntity.ok().body(this.addressService.updateAddress(address, addressId));
	}
	
    @DeleteMapping("/{addressid}/delete")
	public HttpStatus removeAddress(@PathVariable("addressid") int addressId) {
		this.addressService.removeAddress(addressId);
		return HttpStatus.OK;
	}
}
