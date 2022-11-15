package com.pma.web.service;

import com.pma.web.model.Tenant;
import com.pma.web.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TenantServiceImpl implements TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    @Override
    public Tenant addTenant(Tenant tenant) {
    	return tenantRepository.save(tenant);
    }

    @Override
    public Tenant getTenant(long tenantId) {
    	Optional<Tenant> tenantList = this.tenantRepository.findByTenantID(tenantId);
		if (tenantList.isPresent()) {
			return tenantList.get();
		} 
		return null;
    }

    @Override
    public void removeTenant(long tenantId) {
    	Optional<Tenant> tenantList = this.tenantRepository.findByTenantID(tenantId);
		if (tenantList.isPresent()) {
			this.tenantRepository.delete(tenantList.get());
		} 

    }

    @Override
    public Tenant updateTenant(long tenantId, Tenant tenant) {
    	Optional<Tenant> tenantList = this.tenantRepository.findByTenantID(tenantId);
		if (tenantList.isPresent()) {
			Tenant TenantUpdate = tenantList.get();
			TenantUpdate.setName(tenant.getName());
			TenantUpdate.setEmailID(tenant.getEmailID());
			TenantUpdate.setPhoneNO(tenant.getPhoneNO());
			TenantUpdate.setPreviousAddress(tenant.getPreviousAddress());
			tenantRepository.save(TenantUpdate);
			return TenantUpdate;
		}
		return null;
    }

    @Override
    public List<Tenant> getAllTenants() {
    	return (List<Tenant>) tenantRepository.findAll();
    }
}
