package com.pma.web.service;

import com.pma.web.model.Tenant;

import java.util.List;

public interface TenantService {
    public Tenant addTenant(Tenant tenant);

    public Tenant getTenant(long id);

    public void removeTenant(long id);

    public void updateTenant(long id, Tenant tenant);

    public List<Tenant> getAllTenants();

}
