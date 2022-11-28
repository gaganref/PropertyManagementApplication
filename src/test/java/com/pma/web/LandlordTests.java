package com.pma.web;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.pma.web.model.Landlord;
import com.pma.web.repository.LandlordRepository;
import com.pma.web.service.LandlordServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class LandlordTests {
	@InjectMocks
	LandlordServiceImpl ilandlordserviceimpl;

	@Mock
	LandlordRepository ilandlordrepository;

	@Test
	public void getAllLandlordsTest() {
		List<Landlord> LandLord_list = new ArrayList<Landlord>();
		Landlord LandLord_values_1 = new Landlord("Swetha","Swetha@gmail.com","9989876680");
		Landlord LandLord_values_2 = new Landlord("Akhil","Akhil@gmail.com","9989876681");
		Landlord LandLord_values_3 = new Landlord("gagan","gagan@gmail.com","9989876682");

		LandLord_list.add(LandLord_values_1);
		LandLord_list.add(LandLord_values_2);
		LandLord_list.add(LandLord_values_3);

		when(ilandlordrepository.findAll()).thenReturn(LandLord_list);

		List<Landlord> check_car_list = ilandlordserviceimpl.getAllLandlords();

		assertEquals(3, check_car_list.size());
		verify(ilandlordrepository, times(1)).findAll();

	}
	
	@Test
	public void addLandlordsTest() {
		Landlord landlord = new Landlord("Swetha","Swetha@gmail.com","9989876680");
		when(ilandlordrepository.save(landlord)).thenReturn(landlord);

		Landlord LandLord_values = ilandlordserviceimpl.addLandlord(landlord);

		assertEquals("Swetha", LandLord_values.getName());
		assertEquals("Swetha@gmail.com", LandLord_values.getEmailId());
		assertEquals("9989876680", LandLord_values.getPhoneNo());

	}
	
	@Test
	public void removeCarTest() {
		verify(ilandlordrepository, never()).delete(any(Landlord.class));
	}
	
	@Test
	public void updateCarTest() {
		Optional<Landlord> LandLord_list = Optional.of(new Landlord("Swetha","Swetha@gmail.com","9989876680"));

		Landlord LandLord_values = new Landlord("Swetha","Swetha85@gmail.com","9989876680");

		when(ilandlordrepository.findByLandlordId(10)).thenReturn(LandLord_list);

		ilandlordserviceimpl.updateLandlord(10, LandLord_values);

		assertEquals("Swetha", LandLord_values.getName());
		assertEquals("Swetha85@gmail.com", LandLord_values.getEmailId());
		assertEquals("9989876680", LandLord_values.getPhoneNo());
	}
}
