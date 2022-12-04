//package com.pma.web;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import com.pma.web.model.House;
//import com.pma.web.repository.HouseRepository;
//import com.pma.web.service.HouseServiceImpl;
//
//
//@RunWith(MockitoJUnitRunner.class)
//public class HouseTests {
//	@InjectMocks
//	HouseServiceImpl iHouseServiceImpl;
//
//	@Mock
//	HouseRepository iHouseRepository;
//
//	@Test
//	public void getAllHousesTest() {
//		List<House> House_list = new ArrayList<House>();
//
//		House House_1 = new House(10,32,4,2300);
//		House House_2 = new House(15,56,10,1600);
//		House House_3 = new House(45,11,2,4800);
//
//		House_list.add(House_1);
//		House_list.add(House_2);
//		House_list.add(House_3);
//
//		when(iHouseRepository.findAll()).thenReturn(House_list);
//
//		List<House> check_TenancyInfo_list = iHouseServiceImpl.getAllHouses();
//
//		assertEquals(3, check_TenancyInfo_list.size());
//		verify(iHouseRepository, times(1)).findAll();
//
//	}
//
//	@Test
//	public void addHouseTest() {
//		House Houseinformation = new House(15,56,10,1600);
//		when(iHouseRepository.save(Houseinformation)).thenReturn(Houseinformation);
//
//		House House_values = iHouseServiceImpl.addHouse(Houseinformation);
//
//		assertEquals("15", House_values.getLandlord());
//		assertEquals("56", House_values.getAddress());
//		assertEquals("10", House_values.getNoOfRooms());
//		assertEquals("1600", House_values.get_pppm());
//
//	}
//
//	@Test
//	public void removeHouseTest() {
//		verify(iHouseRepository, never()).delete(any(House.class));
//	}
//
//	@Test
//	public void updateHouseTest() {
//		List<House> HouseInfo_List = List.of(new House(15,56,10,1600));
//
//		House House_values = new House(15,56,8,2600);
//
//		when(iHouseRepository.findByLandlord(15)).thenReturn(HouseInfo_List);
//
//		iHouseServiceImpl.updateHouse(10, House_values);
//
//		assertEquals("15", House_values.getLandlord());
//		assertEquals("56", House_values.getAddress());
//		assertEquals("8", House_values.getNoOfRooms());
//		assertEquals("2600", House_values.get_pppm());
//	}
//
//	@Test
//	public void getHouseByCostTest() {
//		List<House> House_list = new ArrayList<House>();
//		House House_values_1 = new House(45,11,2,4800);
//		House House_values_2 = new House(15,56,10,1600);
//		House House_values_3 = new House(10,32,4,2300);
//
//		House_list.add(House_values_1);
//		House_list.add(House_values_2);
//		House_list.add(House_values_3);
//
//		when(iHouseRepository.findAll()).thenReturn(House_list);
//
//		List<House> check_House_list = iHouseServiceImpl.getHouseByCost(1700,6000);
//
//		assertEquals(2, check_House_list.size());
//	}
//
//	@Test
//	public void getHouseByRoomsTest() {
//		List<House> House_list = new ArrayList<House>();
//		House House_values_1 = new House(45,11,2,4800);
//		House House_values_2 = new House(15,56,10,1600);
//		House House_values_3 = new House(10,32,4,2300);
//
//		House_list.add(House_values_1);
//		House_list.add(House_values_2);
//		House_list.add(House_values_3);
//
//		when(iHouseRepository.findAll()).thenReturn(House_list);
//
//		List<House> check_House_list = iHouseServiceImpl.getHouseByRooms(1,15);
//
//		assertEquals(3, check_House_list.size());
//	}
//
//}
