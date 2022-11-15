package com.pma.web.service;

import com.pma.web.model.Tenant;
import com.pma.web.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TenantServiceImpl implements TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    @Override
    public Tenant addTenant(Tenant tenant) {
        return null;
    }

    @Override
    public Tenant getTenant(long id) {
        return null;
    }

    @Override
    public void removeTenant(long id) {

    }

    @Override
    public void updateTenant(long id, Tenant tenant) {

    }

    @Override
    public List<Tenant> getAllTenants() {
        return null;
    }
}
