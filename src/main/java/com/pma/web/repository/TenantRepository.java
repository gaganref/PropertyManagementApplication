package com.pma.web.repository;

import com.pma.web.model.House;
import com.pma.web.model.TenancyInfo;
import com.pma.web.model.Tenant;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
	
	Optional<Tenant> findByTenantId(long tenantID);

	List<Tenant> findByTenancyInfo(TenancyInfo tenancyInfo);


}