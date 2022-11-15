package com.pma.web.repository;

import com.pma.web.model.TenancyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenancyInfoRepository extends JpaRepository<TenancyInfo, Long> {
}
