package com.pma.web.repository;

import com.pma.web.model.TenancyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TenancyInfoRepository extends JpaRepository<TenancyInfo, Long> {

    Optional<TenancyInfo> findTenancyInfoByTenancyInfoID(long tenancyInfoId);

    List<TenancyInfo> findTenancyInfoByStartDateBetween(LocalDate start_date, LocalDate end_date);

    List<TenancyInfo> findTenancyInfoByEndDateBetween(LocalDate start_date, LocalDate end_date);
}
