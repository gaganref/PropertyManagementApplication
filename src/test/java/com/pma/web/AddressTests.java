package com.pma.web;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.pma.web.model.Address;
import com.pma.web.repository.AddressRepository;
import com.pma.web.service.AddressServiceImpl;


@RunWith(MockitoJUnitRunner.class)
public class AddressTests {
	@InjectMocks
	AddressServiceImpl iaddressserviceimpl;

	@Mock
	AddressRepository iaddressrepository;

	@Test
	public void getAllAddressTest() {
		List<Address> LandLord_list = new ArrayList<Address>();
		Address Address_values_1 = new Address(12,34,"Lanchester","Lanchester","DH7 0BL");
		Address Address_values_2 = new Address(45,54,"Lanchester","Lanchester","DH7 0EE");
		Address Address_values_3 = new Address(32,14,"London","London","NW10");

		LandLord_list.add(Address_values_1);
		LandLord_list.add(Address_values_2);
		LandLord_list.add(Address_values_3);

		when(iaddressrepository.findAll()).thenReturn(LandLord_list);

		List<Address> check_car_list = iaddressserviceimpl.getAllAddress("NW10");

		assertEquals(1, check_car_list.size());
		verify(iaddressrepository, times(1)).findAll();

	}
	
	@Test
	public void addAddresssTest() {
		Address landlord = new Address(12,34,"Lanchester","Lanchester","DH7 0BL");
		when(iaddressrepository.save(landlord)).thenReturn(landlord);

		Address Address_values = iaddressserviceimpl.addAddress(landlord);

		assertEquals("12", Address_values.getFlatNo());
		assertEquals("34", Address_values.getHouseNo());
		assertEquals("Lanchester", Address_values.getCity());
		assertEquals("DH7 0BL", Address_values.getPostcode());

	}
	
	@Test
	public void removeAddressTest() {
		verify(iaddressrepository, never()).delete(any(Address.class));
	}
	
	@Test
	public void updateCarTest() {
		List<Address> Address_List = List.of(new Address(12,34,"Lanchester","Lanchester","DH7 0BL"));

		Address Address_values = new Address(12,34,"Lanchester","Lanchester city","DH7 0BL");

		when(iaddressrepository.findByPostcode("DH7 0BL")).thenReturn(Address_List);

		iaddressserviceimpl.updateAddress(Address_values, 10);

		assertEquals("12", Address_values.getFlatNo());
		assertEquals("34", Address_values.getHouseNo());
		assertEquals("Lanchester city", Address_values.getCity());
		assertEquals("DH7 0BL", Address_values.getPostcode());
	}
}
