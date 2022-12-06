package com.pma.web.repository;

import com.pma.web.model.Landlord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LandlordRepository extends JpaRepository<Landlord, Long> {
	
	Optional<Landlord> findByLandlordId(long landlordId);

}