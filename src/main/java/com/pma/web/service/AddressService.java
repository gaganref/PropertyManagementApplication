package com.pma.web.service;

import com.pma.web.model.Address;
import java.util.List;

public interface AddressService {
	
	public Address addAddress(Address address);

	public void removeAddress(int addressId);

	public List<Address> getAllAddress(String postcode);
	
	public Address getAddress(int addressId);
	
	public Address updateAddress(Address address, int addressId);
	
}