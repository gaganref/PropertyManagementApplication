package com.pma.web.service;

import com.pma.web.exception.ModelAddException;
import com.pma.web.exception.ModelEmptyListException;
import com.pma.web.exception.ModelNotFoundException;
import com.pma.web.exception.ModelUpdateException;
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
    	try {
    		return tenantRepository.save(tenant);
        } catch (Exception e) {
            throw new ModelAddException("Couldn't add the model Tenant, please add the proper details");
        }
    }

    @Override
    public Tenant getTenant(long tenantId) {
		try {
			Optional<Tenant> tenantList = this.tenantRepository.findByTenantId(tenantId);
			if (tenantList.isPresent()) {
				return tenantList.get();
			} 
            else{
                throw new ModelNotFoundException("Couldn't find the Tenant of id: " + tenantId);
            }
        } catch (Exception e) {
            throw new ModelNotFoundException("Couldn't find the Tenant of id: " + tenantId);
        }
    }

    @Override
    public void removeTenant(long tenantId) { 
		try {
			Optional<Tenant> tenantList = this.tenantRepository.findByTenantId(tenantId);
			if (tenantList.isPresent()) {
				this.tenantRepository.delete(tenantList.get());
			} 
            else{
                throw new ModelNotFoundException("Couldn't find the Tenant of id: " + tenantId);
            }
        } catch (Exception e) {
            throw new ModelNotFoundException("Couldn't remove Tenant of id: " + tenantId + " as it is not present");
        }
    }

    @Override
    public Tenant updateTenant(long tenantId, Tenant tenant) {
		try {
			Optional<Tenant> tenantList = this.tenantRepository.findByTenantId(tenantId);
			if (tenantList.isPresent()) {
				Tenant TenantUpdate = tenantList.get();
				TenantUpdate.setName(tenant.getName());
				TenantUpdate.setEmailId(tenant.getEmailId());
				TenantUpdate.setPhoneNo(tenant.getPhoneNo());
				TenantUpdate.setPreviousAddress(tenant.getPreviousAddress());
				tenantRepository.save(TenantUpdate);
				return TenantUpdate;
			}
            else{
                throw new ModelUpdateException("Couldn't update Tenant of id: " + tenantId);
            }
        } catch (Exception e) {
            throw new ModelUpdateException("Couldn't update Tenant of id: " + tenantId + " as it is not present");
        }
    }

    @Override
    public List<Tenant> getAllTenants() {
    	try {
    		return (List<Tenant>) tenantRepository.findAll();
        } catch (Exception e) {
            throw new ModelEmptyListException("Error retrieving Tenants... please try again");
        }
    }
}
