package com.pma.web.repository;

import com.pma.web.model.TenancyInfo;
import com.pma.web.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
	
	Optional<Tenant> findByTenantId(long tenantID);

	List<Tenant> findByTenancyInfo(TenancyInfo tenancyInfo);


}