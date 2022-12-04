//package com.pma.web.repository;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import com.pma.web.model.Address;
//
//@Repository
//public interface AddressRepository extends JpaRepository<Address, Long> {
//
//	Optional<Address> findByAddressId(int addressId);
//
//	List<Address> findByPostcode(String postcode);
//
//}