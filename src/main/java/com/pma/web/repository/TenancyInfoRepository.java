package com.pma.web.repository;

import com.pma.web.model.TenancyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TenancyInfoRepository extends JpaRepository<TenancyInfo, Long> {

    Optional<TenancyInfo> findTenancyInfoByTenancyInfoID(long tenancyInfoId);

    List<TenancyInfo> findTenancyInfoByStartDateBetween(Date startDate, Date startDate2);

    List<TenancyInfo> findTenancyInfoByEndDateBetween(Date endDate, Date endDate2);
}
