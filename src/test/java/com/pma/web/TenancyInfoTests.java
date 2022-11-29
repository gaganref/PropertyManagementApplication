package com.pma.web;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.pma.web.model.TenancyInfo;
import com.pma.web.repository.TenancyInfoRepository;
import com.pma.web.service.TenancyInfoServiceImpl;


@RunWith(MockitoJUnitRunner.class)
public class TenancyInfoTests {
	@InjectMocks
	TenancyInfoServiceImpl iTenancyInfoServiceImpl;

	@Mock
	TenancyInfoRepository iTenancyInfoRepository;

	@Test
	public void getAllTenancyInfosTest() {
		List<TenancyInfo> TenancyInfo_list = new ArrayList<TenancyInfo>();
		LocalDate start_date = LocalDate.of(2020, 10, 11);
		LocalDate start_date2 = LocalDate.of(2018, 10, 11);
		LocalDate end_date = LocalDate.of(2022, 10, 12);
		LocalDate end_date2 = LocalDate.of(2021, 10, 12);
		TenancyInfo TenancyInfo_1 = new TenancyInfo(122,344,start_date,end_date);
		TenancyInfo TenancyInfo_2 = new TenancyInfo(188,3745,start_date2,end_date);
		TenancyInfo TenancyInfo_3 = new TenancyInfo(876,8944,start_date2,end_date2);

		TenancyInfo_list.add(TenancyInfo_1);
		TenancyInfo_list.add(TenancyInfo_2);
		TenancyInfo_list.add(TenancyInfo_3);

		when(iTenancyInfoRepository.findAll()).thenReturn(TenancyInfo_list);

		List<TenancyInfo> check_TenancyInfo_list = iTenancyInfoServiceImpl.getAllTenancyInfos();

		assertEquals(3, check_TenancyInfo_list.size());
		verify(iTenancyInfoRepository, times(1)).findAll();

	}
	
	@Test
	public void addTenancyInfoTest() {
		LocalDate start_date = LocalDate.of(2020, 10, 11);
		LocalDate end_date = LocalDate.of(2022, 10, 12);
		TenancyInfo Tenancyinformation = new TenancyInfo(122,344,start_date,end_date);
		when(iTenancyInfoRepository.save(Tenancyinformation)).thenReturn(Tenancyinformation);

		TenancyInfo TenancyInfo_values = iTenancyInfoServiceImpl.addTenancyInfo(Tenancyinformation);

		assertEquals("122", TenancyInfo_values.getHouse());
		assertEquals("344", TenancyInfo_values.getTenant());
		assertEquals(start_date, TenancyInfo_values.getStartDate());
		assertEquals(end_date, TenancyInfo_values.getEndDate());

	}
	
	@Test
	public void removeTenancyInfoTest() {
		verify(iTenancyInfoRepository, never()).delete(any(TenancyInfo.class));
	}
	
	@Test
	public void updateTenancyInfoTest() {
		LocalDate start_date = LocalDate.of(2020, 10, 11);
		LocalDate end_date = LocalDate.of(2022, 10, 12);
		Optional<TenancyInfo> TenancyInfo_List = Optional.of(new TenancyInfo(122,344,start_date,end_date));

		LocalDate new_end_date = LocalDate.of(2022, 10, 12);
		TenancyInfo TenancyInfo_values = new TenancyInfo(1224,344,start_date,new_end_date);

		when(iTenancyInfoRepository.findTenancyInfoByTenancyInfoID(10)).thenReturn(TenancyInfo_List);

		iTenancyInfoServiceImpl.updateTenancyInfo(10, TenancyInfo_values);

		assertEquals("1224", TenancyInfo_values.getHouse());
		assertEquals("344", TenancyInfo_values.getTenant());
		assertEquals(start_date, TenancyInfo_values.getStartDate());
		assertEquals(new_end_date, TenancyInfo_values.getEndDate());
	}
}
