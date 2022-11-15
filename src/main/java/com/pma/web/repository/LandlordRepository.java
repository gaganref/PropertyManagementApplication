package com.pma.web.repository;

import org.springframework.stereotype.Repository;

import com.pma.web.model.Landlord;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LandlordRepository extends JpaRepository<Landlord, Long> {
	
	Optional<Landlord> findByLandlordId(int landlordId);

}