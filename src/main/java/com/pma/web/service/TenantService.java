package com.pma.web.service;

import com.pma.web.model.Tenant;

import java.util.List;

public interface TenantService {
    public Tenant addTenant(Tenant tenant);

    public Tenant getTenant(long tenantId);

    public void removeTenant(long tenantId);

    public Tenant updateTenant(long tenantId, Tenant tenant);

    public List<Tenant> getAllTenants();

}
