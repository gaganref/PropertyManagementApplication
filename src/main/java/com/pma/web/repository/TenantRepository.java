package com.pma.web.repository;

import com.pma.web.model.Tenant;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
	
	Optional<Tenant> findByTenantID(long tenantID);


}