package com.pma.web;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.pma.web.model.Tenant;
import com.pma.web.repository.TenantRepository;
import com.pma.web.service.TenantServiceImpl;



@RunWith(MockitoJUnitRunner.class)
public class TenantTests {
	@InjectMocks
	TenantServiceImpl iTenantServiceImpl;

	@Mock
	TenantRepository iTenantRepository;

	@Test
	public void getAllTenantsTest() {
		List<Tenant> Tenant_list = new ArrayList<Tenant>();
		Tenant Tenant_values_1 = new Tenant("varshini","varshini@gmail.com","9999988888",10L);
		Tenant Tenant_values_2 = new Tenant("varshini2","varshini2@gmail.com","9999988889",20L);
		Tenant Tenant_values_3 = new Tenant("varshini3","varshini3@gmail.com","9999988880",30L);

		Tenant_list.add(Tenant_values_1);
		Tenant_list.add(Tenant_values_2);
		Tenant_list.add(Tenant_values_3);

		when(iTenantRepository.findAll()).thenReturn(Tenant_list);

		List<Tenant> check_Tenant_list = iTenantServiceImpl.getAllTenants();

		assertEquals(3, check_Tenant_list.size());
		verify(iTenantRepository, times(1)).findAll();

	}
	
	@Test
	public void addTenantTest() {
		Tenant Tenant_values = new Tenant("varshini","varshini@gmail.com","9999988888",10L);
		when(iTenantRepository.save(Tenant_values)).thenReturn(Tenant_values);

		Tenant tenant_values = iTenantServiceImpl.addTenant(Tenant_values);

		assertEquals("varshini", tenant_values.getName());
		assertEquals("varshini@gmail.com", tenant_values.getEmailID());
		assertEquals("9999988888", tenant_values.getPhoneNO());
		assertEquals(10L, tenant_values.getPreviousAddress());

	}
	
	@Test
	public void removeTenantTest() {
		verify(iTenantRepository, never()).delete(any(Tenant.class));
	}
	
	@Test
	public void updateTenantTest() {
		Optional<Tenant> Tenant_List = Optional.of(new Tenant("varshini","varshini@gmail.com","9999988888",10L));

		Tenant Tenant_values = new Tenant("varshini","varshini@gmail.com","9998988888",30L);

		when(iTenantRepository.findByTenantID(10)).thenReturn(Tenant_List);

		iTenantServiceImpl.updateTenant(10, Tenant_values);

		assertEquals("varshini", Tenant_values.getName());
		assertEquals("varshini@gmail.com", Tenant_values.getEmailID());
		assertEquals("9998988888", Tenant_values.getPhoneNO());
		assertEquals(30L, Tenant_values.getPreviousAddress());
	}
}
